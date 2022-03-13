package johan.spekman.novibeie.module_customer_address.controller;

import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.service.CustomerAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/customer/address/save")
                        .toUriString());
        return ResponseEntity.created(uri).body(customerAddressService.createNewAddress(customerAddressDto,
                bindingResult));
    }
}
