package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface CustomerAddressService {
    CustomerAddress createNewAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto,
                                     BindingResult bindingResult);
    CustomerAddress getShippingAddress(Customer customer);
    CustomerAddress getBillingAddress(Customer customer);
}
