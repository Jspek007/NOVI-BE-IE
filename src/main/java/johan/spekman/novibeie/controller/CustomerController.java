package johan.spekman.novibeie.controller;

import johan.spekman.novibeie.repository.CustomerRepository;
import johan.spekman.novibeie.model.Customer;
import johan.spekman.novibeie.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<Object> getGuests() {
        return customerService.getAllCustomers();
    }

//    @PostMapping(path = "/customers")
//    public ResponseEntity<Object> createNewCustomer(@Valid @RequestBody Customer customer,
//                                                    BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            StringBuilder stringBuilder = new StringBuilder();
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                stringBuilder.append(fieldError.getDefaultMessage());
//                stringBuilder.append("\n");
//            }
//            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
//        }
//        else {
//            customerRepository.save(customer);
//            return new ResponseEntity<>(customer, HttpStatus.CREATED);
//        }
//    }
}
