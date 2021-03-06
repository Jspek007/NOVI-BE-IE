package johan.spekman.novibeie.module_customer.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.service.CustomerService;
import johan.spekman.novibeie.utililies.CSVFormatCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/get/{email}")
    public ResponseEntity<Customer> getCustomerByEmailAddress(@PathVariable("email") String customerEmail) {
        if (customerService.getCustomerByEmailAddress(customerEmail) == null) {
            throw new ApiRequestException("No customer found with this email adress: " + customerEmail);
        } else {
            return ResponseEntity.ok().body(customerService.getCustomerByEmailAddress(customerEmail));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> saveNewCustomer(@Valid @RequestBody CustomerDto customerDto,
                                                  BindingResult bindingResult) {
        Customer customer = customerService.getCustomerByEmailAddress(customerDto.getEmailAddress());
        if (customer != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account with this e-mail already exists!");
        }

        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/customer/save").toUriString());
        return ResponseEntity.created(uri).body(customerService.createCustomer(customerDto, bindingResult));
    }

    @DeleteMapping("/delete/{email}")
    public void deleteCustomerByEmail(@PathVariable("email") String customerEmail) {
        customerService.deleteCustomerByCustomerEmail(customerEmail);
    }

    @PutMapping(path = "/update/{email}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("email") String customerEmail,
                                                 @Valid @RequestBody CustomerDto newCustomer, BindingResult bindingResult) {
        ResponseEntity<Object> customer = customerService.updateCustomer(customerEmail, newCustomer, bindingResult);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping(path = "/export/all", produces = "text/csv")
    public void exportAllCustomers(HttpServletResponse response) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        /*
            write actual data to CSV file
         */
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"customers_" + format.format(date) + ".csv\"");
        customerService.exportCustomersToCsv(response.getWriter());
    }

    @PostMapping(path = "/import")
    public ResponseEntity<Object> importCustomers(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (!CSVFormatCheck.hasCSVFormat(file)) {
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            try {
                customerService.saveAll(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception exception) {
                message = "Could not upload the file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
    }
}
