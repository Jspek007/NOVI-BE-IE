package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final InputValidation inputValidation;

    public CustomerServiceImpl(CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            InputValidation inputValidation) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.inputValidation = inputValidation;
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
        if (inputValidation.validate(bindingResult) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputValidation.validate(bindingResult));
        } else {
            String encryptedPassword = passwordEncoder.encode(customerDto.getPassword());

            Random random = new Random();
            long low = 100000L;
            long high = 999999L;
            Long customerId = random.nextLong(high - low) + low;

            Customer customer = new Customer();
            if (!CustomerValidation.checkCustomerPhoneNumber(customerDto.getPhoneNumber())) {
                return new ResponseEntity<>("Incorrect phone number format", HttpStatus.BAD_REQUEST);
            } else {
                customer.setPhoneNumber(customerDto.getPhoneNumber());
            }
            Customer customerByEmail = customerRepository.findByEmailAddress(customerDto.getEmailAddress());
            if (customerByEmail != null) {
                return new ResponseEntity<>("Account with this e-mail already exists", HttpStatus.FORBIDDEN);
            }
            customer.setFirstName(customerDto.getFirstName());
            customer.setInsertion(customerDto.getInsertion());
            customer.setLastName(customerDto.getLastName());
            customer.setPassword(encryptedPassword);
            customer.setEmailAddress(customerDto.getEmailAddress());
            customer.setCustomerId(customerId);

            Customer savedCustomer = customerRepository.save(customer);
            return new ResponseEntity<>("Customer has been created: " + savedCustomer, HttpStatus.CREATED);
        }
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        Long entityId = customerRepository.findByCustomerId(customerId).getId();
        customerRepository.deleteById(entityId);
    }

    @Override
    public ResponseEntity<Object> updateCustomer(Long customerId, CustomerDto newCustomerDto,
            BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException("Request could not be processed: " + bindingResult.getFieldErrorCount());
        } else {
            Customer foundCustomer = customerRepository.findByCustomerId(customerId);
            String encryptedPassword = passwordEncoder.encode(newCustomerDto.getPassword());

            if (foundCustomer != null) {
                try {
                    foundCustomer.setFirstName(newCustomerDto.getFirstName());
                    foundCustomer.setInsertion(newCustomerDto.getInsertion());
                    foundCustomer.setLastName(newCustomerDto.getLastName());
                    foundCustomer.setEmailAddress(newCustomerDto.getEmailAddress());
                    foundCustomer.setPhoneNumber(newCustomerDto.getPhoneNumber());
                    foundCustomer.setPassword(encryptedPassword);

                    customerRepository.save(foundCustomer);
                    return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
                } catch (Exception exception) {
                    throw new ApiRequestException("Customer could not be saved: " + exception.getMessage());
                }
            }
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @Override
    public void exportCustomersToCsv(Writer writer) {
        String[] headers = { "Id", "Customer Id", "Firstname", "Insertion", "Lastname", "E-mail", "Password",
                "Phone number" };

        List<Customer> customerList = getAllCustomers();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            for (Customer customer : customerList) {
                csvPrinter.printRecord(customer.getId(), customer.getCustomerId(), customer.getFirstName(),
                        customer.getInsertion(), customer.getLastName(), customer.getEmailAddress(),
                        customer.getPassword(), customer.getPhoneNumber());
            }
        } catch (IOException exception) {
            System.out.println("Error while creating Csv file: " + exception);
        }
    }

    @Override
    public List<Customer> csvToCustomers(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Customer> customers = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Customer customer = new Customer(
                        Long.parseLong(csvRecord.get("Id")),
                        Long.parseLong(csvRecord.get("Customer Id")),
                        csvRecord.get("Firstname"),
                        csvRecord.get("Insertion"),
                        csvRecord.get("Lastname"),
                        csvRecord.get("E-mail"),
                        csvRecord.get("Password"),
                        csvRecord.get("Phone number"));
                customers.add(customer);
            }
            return customers;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse CSV file: " + exception.getMessage());
        }
    }

    @Override
    public void saveAll(MultipartFile file) {
        try {
            List<Customer> customerList = csvToCustomers(file.getInputStream());
            customerRepository.saveAll(customerList);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store csv data: " + exception.getMessage());
        }
    }
}
