package johan.spekman.novibeie.module_customer.service.ImportService;

import johan.spekman.novibeie.module_customer.model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvImportService {
    public static String TYPE = "text/csv";
    static String[] headers = {"Id", "Customer Id", "Firstname", "Insertion", "Lastname", "E-mail", "Password", "Phone number"};

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Customer> csvToCustomers(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
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
                        csvRecord.get("Phone number")
                );
                customers.add(customer);
            }
            return customers;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse CSV file: " + exception.getMessage());
        }
    }

}
