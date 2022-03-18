package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerByEmailAddress(String emailAddress) {
        return customerRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public ResponseEntity<Object> createCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        // Validate user input before attempting to create a new customer
        if (inputValidation.validate(bindingResult) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputValidation.validate(bindingResult));
        } else {
            // Encrypt the password of the user
            String encryptedPassword = passwordEncoder.encode(customerDto.getPassword());

            // Generate the random customerId
            Random random = new Random();
            long low = 100000L;
            long high = 999999L;
            Long customerId = random.nextLong(high - low) + low;

            Customer customer = new Customer();
            /*
                Validate the given phone-number for the correct
                Dutch format (+31612345678)
             */

            if (!CustomerValidation.checkCustomerPhoneNumber(customerDto.getPhoneNumber())) {
                return new ResponseEntity<>("Incorrect phone number format", HttpStatus.BAD_REQUEST);
            } else {
                customer.setPhoneNumber(customerDto.getPhoneNumber());
            }

            /*
                Check if given email is already used for an existing customer
             */
            Customer customerByEmail = customerRepository.findByEmailAddress(customerDto.getEmailAddress());
            if (customerByEmail != null) {
                return new ResponseEntity<>("Account with this e-mail already exists", HttpStatus.FORBIDDEN);
            }

            /*
                Create the actual new customer
             */
            customer.setFirstName(customerDto.getFirstName());
            customer.setInsertion(customerDto.getInsertion());
            customer.setLastName(customerDto.getLastName());
            customer.setPassword(encryptedPassword);
            customer.setEmailAddress(customerDto.getEmailAddress());
            customer.setCustomerId(customerId);

            Customer savedCustomer = customerRepository.save(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        }
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        Long entityId = customerRepository.findByCustomerId(customerId).getId();
        customerRepository.deleteById(entityId);
    }

    @Override
    public ResponseEntity<Object> updateCustomer(Long customerId, CustomerDto newCustomerDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        }

        Customer foundCustomer = customerRepository.findByCustomerId(customerId);
        String encryptedPassword = passwordEncoder.encode(newCustomerDto.getPassword());

        if (foundCustomer != null) {
            foundCustomer.setFirstName(newCustomerDto.getFirstName());
            foundCustomer.setInsertion(newCustomerDto.getInsertion());
            foundCustomer.setLastName(newCustomerDto.getLastName());
            foundCustomer.setEmailAddress(newCustomerDto.getEmailAddress());
            foundCustomer.setPhoneNumber(newCustomerDto.getPhoneNumber());
            foundCustomer.setPassword(encryptedPassword);

            customerRepository.save(foundCustomer);
            return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @Override
    public void exportAllCustomers() {

    }
}
