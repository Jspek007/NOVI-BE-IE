package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@Transactional
public class CsvExportService {
    private static final Logger logger = getLogger(CsvExportService.class);

    private final CustomerService customerService;

    public CsvExportService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void exportCustomersToCsv(Writer writer) {
        List<Customer> customerList = customerService.getAllCustomers();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (Customer customer : customerList) {
                csvPrinter.printRecord(customer.getCustomerId(), customer.getCustomerId(), customer.getFirstName(),
                        customer.getInsertion(), customer.getLastName(), customer.getEmailAddress(),
                        customer.getPassword(), customer.getPhoneNumber());
            }
        } catch (IOException exception) {
            logger.error("Error while writing CSV", exception);
        }
    }
}
