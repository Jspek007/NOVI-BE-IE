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
    public Customer createCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult) {
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException(bindingResult.getFieldError().toString());
        } else {
            String encryptedPassword = passwordEncoder.encode(customerDto.getPassword());

            Random random = new Random();
            long low = 100000L;
            long high = 999999L;
            Long customerId = random.nextLong(high - low) + low;

            Customer customer = new Customer();
            if (!CustomerValidation.checkCustomerPhoneNumber(customerDto.getPhoneNumber())) {
                throw new ApiRequestException("Incorrect phone number format");
            } else {
                customer.setPhoneNumber(customerDto.getPhoneNumber());
            }
            if (customerRepository.findByEmailAddress(customerDto.getEmailAddress()) != null) {
                throw new ApiRequestException("Account with this e-mail already exists");
            }
            customer.setFirstName(customerDto.getFirstName());
            customer.setInsertion(customerDto.getInsertion());
            customer.setLastName(customerDto.getLastName());
            customer.setPassword(encryptedPassword);
            customer.setEmailAddress(customerDto.getEmailAddress());
            customer.setCustomerId(customerId);

            Customer savedCustomer = customerRepository.save(customer);
            return savedCustomer;
        }
    }

    @Override
    public void deleteCustomerByCustomerEmail(String customerEmail) {
        customerRepository.deleteByEmailAddress(customerEmail);
    }

    @Override
    public ResponseEntity<Object> updateCustomer(String customerEmail, CustomerDto newCustomerDto,
                                                 BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException("Request could not be processed: " + bindingResult.getFieldError().toString());
        } else {
            Customer foundCustomer = customerRepository.findByEmailAddress(customerEmail);
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
        String[] headers = {"Id", "Customer Id", "Firstname", "Insertion", "Lastname", "E-mail", "Password",
                "Phone number"};

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
                Customer customer = new Customer();
                customer.setId(Long.valueOf(csvRecord.get("Id")));
                customer.setCustomerId(Long.valueOf(csvRecord.get("Customer Id")));
                customer.setFirstName(csvRecord.get("Firstname"));
                customer.setInsertion(csvRecord.get("Insertion"));
                customer.setLastName(csvRecord.get("Lastname"));
                customer.setEmailAddress(csvRecord.get("E-mail"));
                customer.setPassword(csvRecord.get("Password"));
                customer.setPhoneNumber(csvRecord.get("Phone number"));
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
