package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressServiceImpl(CustomerRepository customerRepository,
            CustomerAddressRepository customerAddressRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public ResponseEntity<Object> createNewAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto,
            BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException("Malformed input: " + bindingResult.getFieldError());
        }
        if (!CustomerAddressValidation.checkPostalCode(customerAddressDto.getPostalCode())) {
            throw new ApiRequestException("Postal code is not valid");
        }
        Customer customer = customerRepository.findByEmailAddress(customerAddressDto.getCustomerEmail());
        if (customer == null) {
            throw new ApiRequestException("Customer not found");
        }

        CustomerAddress defaultBillingAddress =
                customerAddressRepository.getCustomerAddressByCustomerAndType(customer.getId(), "billing");
        CustomerAddress defaultShippingAddress =
                customerAddressRepository.getCustomerAddressByCustomerAndType(customer.getId(), "shipping");

        if (customerAddressDto.isDefaultAddress() && customerAddressDto.getCustomerAddressType() == CustomerAddressType.billing) {
            defaultBillingAddress.setDefaultAddress(false);
            customerAddressRepository.save(defaultBillingAddress);
        }

        if (customerAddressDto.isDefaultAddress() && customerAddressDto.getCustomerAddressType() == CustomerAddressType.shipping) {
            defaultShippingAddress.setDefaultAddress(false);
            customerAddressRepository.save(defaultShippingAddress);
        }

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setPostalCode(customerAddressDto.getPostalCode());
        customerAddress.setCustomerId(customer.getCustomerId());
        customerAddress.setStreetName(customerAddressDto.getStreetName());
        customerAddress.setHouseNumber(customerAddressDto.getHouseNumber());
        customerAddress.setAddition(customerAddressDto.getAddition());
        customerAddress.setPostalCode(customerAddressDto.getPostalCode());
        customerAddress.setCity(customerAddressDto.getCity());
        customerAddress.setCustomerAddressType(customerAddressDto.getCustomerAddressType());
        customerAddress.setDefaultAddress(customerAddressDto.isDefaultAddress());

        customer.addCustomerAddress(customerAddress);
        return new ResponseEntity<>(customerAddress, HttpStatus.CREATED);
    }

    @Override
    public CustomerAddress getShippingAddress(Customer customer) {
        Long customerId = customerRepository.findByEmailAddress(customer.getEmailAddress()).getId();
        return customerAddressRepository.getCustomerAddressByCustomerAndType(customerId, "shipping");
    }

    @Override
    public CustomerAddress getBillingAddress(Customer customer) {
        return customerAddressRepository.getCustomerAddressByCustomerAndType(customer.getId(), "billing");
    }
}
