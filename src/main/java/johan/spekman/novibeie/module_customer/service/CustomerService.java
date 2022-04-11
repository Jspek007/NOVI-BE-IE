package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.io.Writer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerByEmailAddress(String emailAddress);

    ResponseEntity<Object> createCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult);

    void saveAll(MultipartFile file);

    void deleteCustomerById(Long customerId);

    ResponseEntity<Object> updateCustomer(Long customerId, CustomerDto newCustomerDto, BindingResult bindingResult);

    void exportCustomersToCsv(Writer writer);

    List<Customer> csvToCustomers(InputStream inputStream);
}
