package johan.spekman.novibeie.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<Object> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @Transactional
    public Optional <Customer> findByEmailAddress(String email) {
        return customerRepository.findByEmailAddress(email);
    }

    public boolean customerEmailExists(String email) {
        return findByEmailAddress(email).isPresent();
    }
}
