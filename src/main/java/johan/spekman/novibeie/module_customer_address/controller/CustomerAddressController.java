package johan.spekman.novibeie.module_customer_address.controller;

import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.service.CustomerAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer/address")
public class CustomerAddressController {
    private final CustomerAddressService customerAddressService;

    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveNewAddress(
            @Valid @RequestBody
            CustomerAddressDto customerAddressDto,
            BindingResult bindingResult) {
        return ResponseEntity.ok().body(customerAddressService.createNewAddress(customerAddressDto, bindingResult));
    }
}
