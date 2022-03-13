package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Transactional
public class CustomerAddressServiceImpl implements CustomerAddressService {
    private final CustomerRepository customerRepository;

    public CustomerAddressServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<Object> createNewAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        } else {
            Long customerId = customerAddressDto.getParentId();
            Customer customer = customerRepository.findByCustomerId(customerId);

            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setCustomerId(customer.getCustomerId());
            customerAddress.setStreetName(customerAddressDto.getStreetName());
            customerAddress.setHouseNumber(customerAddressDto.getHouseNumber());
            customerAddress.setAddition(customerAddressDto.getAddition());
            customerAddress.setPostalCode(customerAddressDto.getPostalCode());
            customerAddress.setCity(customerAddressDto.getCity());

            if (!CustomerAddressValidation.checkPostalCode(customerAddressDto.getPostalCode())) {
                return new ResponseEntity<>("Incorrect postal code format", HttpStatus.BAD_REQUEST);
            } else {
                customerAddress.setPostalCode(customerAddressDto.getPostalCode());
            }

            customer.addCustomerAddress(customerAddress);
            return new ResponseEntity<>(customerAddress, HttpStatus.CREATED);
        }
    }
}
