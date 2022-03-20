package johan.spekman.novibeie.module_customer.service.ExportService;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.service.CustomerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public record CsvExportService(CustomerService customerService) {
    private static final Logger logger = getLogger(CsvExportService.class);
    static String[] headers = {"Id", "Customer Id", "Firstname", "Insertion", "Lastname", "E-mail", "Password", "Phone number"};

    public void exportCustomersToCsv(Writer writer) {
        List<Customer> customerList = customerService.getAllCustomers();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            for (Customer customer : customerList) {
                csvPrinter.printRecord(customer.getId(), customer.getCustomerId(), customer.getFirstName(),
                        customer.getInsertion(), customer.getLastName(), customer.getEmailAddress(),
                        customer.getPassword(), customer.getPhoneNumber());
            }
        } catch (IOException exception) {
            logger.error("Error while writing CSV", exception);
        }
    }
}
