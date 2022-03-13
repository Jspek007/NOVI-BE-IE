package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerByEmailAddress(String emailAddress);
    ResponseEntity<Object> createCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult);
    void deleteCustomerById(Long customerId);
    ResponseEntity<Object> updateCustomer(@PathVariable("customerId") Long customerId,
                                          @Valid @RequestBody CustomerDto newCustomerDto);
}
