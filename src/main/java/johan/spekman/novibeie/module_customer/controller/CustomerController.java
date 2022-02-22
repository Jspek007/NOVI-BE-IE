package johan.spekman.novibeie.module_customer.controller;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("get/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveNewCustomer(@Valid @RequestBody CustomerDto customerDto,
                                                  BindingResult bindingResult) {
        return ResponseEntity.ok().body(customerService.createCustomer(customerDto, bindingResult));
    }
}
