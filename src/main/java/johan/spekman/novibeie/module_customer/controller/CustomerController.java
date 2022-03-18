package johan.spekman.novibeie.module_customer.controller;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.service.ExportService.CsvExportService;
import johan.spekman.novibeie.module_customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private final CsvExportService csvExportService;

    public CustomerController(CustomerService customerService, CsvExportService csvExportService) {
        this.customerService = customerService;
        this.csvExportService = csvExportService;
    }

    @GetMapping("/get/all")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/get/{email}")
    public Customer getCustomerByEmailAddress(@PathVariable("email") String customerEmail) {
        return customerService.getCustomerByEmailAddress(customerEmail);
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

    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable("id") Long customerId) {
        customerService.deleteCustomerById(customerId);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("id") Long customerId,
                                                 @Valid @RequestBody CustomerDto newCustomer, BindingResult bindingResult) {
        ResponseEntity<Object> customer = customerService.updateCustomer(customerId, newCustomer, bindingResult);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping(path = "/export/all", produces = "text/csv")
    public void exportAllCustomers(HttpServletResponse response) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        /*
            write actual data to CSV file
         */
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"customers_" + format.format(date) + ".csv\"");
        csvExportService.exportCustomersToCsv(response.getWriter());
    }
}
