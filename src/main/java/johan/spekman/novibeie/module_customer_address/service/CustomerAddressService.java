package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface CustomerAddressService {
    ResponseEntity<Object> createNewAddress(@Valid @RequestBody CustomerAddressDto customerAddressDto,
                                            BindingResult bindingResult);
}
