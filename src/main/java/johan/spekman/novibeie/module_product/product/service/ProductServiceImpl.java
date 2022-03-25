package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Override
    public void deleteProductBySku(String sku) {
        Long productId = productRepository.findBySku(sku).getId();
        productRepository.deleteById(productId);
        ResponseEntity.ok("Product with sku: " + sku + " has been deleted.");
    }

    @Override
    public ResponseEntity<Object> updateProduct(String sku, ProductDto productDto, BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputValidation.validate(bindingResult));
        } else {
            Product existingProduct = productRepository.findBySku(sku);

            if (existingProduct == null) {
                return null;
            } else {
                existingProduct.setSku(productDto.getSku());
                existingProduct.setProductTitle(productDto.getProductTitle());
                existingProduct.setProductDescription(productDto.getProductDescription());
                existingProduct.setProductPrice(productDto.getProductPrice());
                existingProduct.setEnabled(productDto.isEnabled());
                productRepository.save(existingProduct);

                return new ResponseEntity<>(existingProduct, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<Object> createProduct(ProductDto productDto, BindingResult bindingResult) throws ParseException {
        // Validate the input before attempting to create a new product
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputValidation.validate(bindingResult));
        } else {
            Product product = new Product();
            Date date = new Date(System.currentTimeMillis());
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
            String currentDate = format.format(date);
            Date setDate = format.parse(currentDate);


            product.setSku(productDto.getSku());
            product.setProductTitle(productDto.getProductTitle());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductPrice(productDto.getProductPrice());
            product.setCreatedAtDate(setDate);
            product.setEnabled(productDto.isEnabled());

            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }
    }

    @Override
    public void productExport(Writer writer) {
        String[] headers = {"Id", "sku", "productTitle", "productDescription", "productPrice", "taxPercentage",
                "createdAtDate", "isEnabled"};

        List<Product> productList = productRepository.findAll();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
            for (Product product : productList) {
                csvPrinter.printRecord(
                        product.getId(),
                        product.getSku(),
                        product.getProductTitle(),
                        product.getProductDescription(),
                        product.getProductPrice(),
                        product.getTaxPercentage(),
                        product.getCreatedAtDate(),
                        product.isEnabled()
                );
            }
        } catch (IOException exception) {
            System.out.println("Error while creating Csv file: " + exception);
        }
    }

    @Override
    public List<Product> productImport(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Product> productList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                Date date = simpleDateFormat.parse(csvRecord.get("createdAtDate"));
                System.out.println(date);
                Product product = new Product(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("sku"),
                        csvRecord.get("productTitle"),
                        csvRecord.get("productDescription"),
                        Double.parseDouble(csvRecord.get("productPrice")),
                        Integer.parseInt(csvRecord.get("taxPercentage")),
                        date,
                        Boolean.parseBoolean(csvRecord.get("isEnabled"))
                );
                productList.add(product);
            }
            return productList;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse CSV file: " + exception.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveAll(MultipartFile file) {
        try {
            List<Product> productList = productImport(file.getInputStream());
            productRepository.saveAll(productList);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store csv data: " + exception.getMessage());
        }
    }
}
