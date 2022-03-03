package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerByEmailAddress(String emailAddress);
    ResponseEntity<Object> createCustomer(@Valid CustomerDto customerDto,
                                          BindingResult bindingResult);
}
