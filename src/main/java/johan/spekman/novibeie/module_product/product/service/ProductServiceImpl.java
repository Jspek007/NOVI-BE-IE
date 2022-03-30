package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final DuplicateProductCheck duplicateProductCheck;

    public ProductServiceImpl(ProductRepository productRepository, DuplicateProductCheck duplicateProductCheck) {
        this.productRepository = productRepository;
        this.duplicateProductCheck = duplicateProductCheck;
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
        if (productRepository.findBySku(sku) == null) {
            throw new ApiRequestException(sku + " No product found with this SKU.");
        }
        productRepository.deleteById(productRepository.findBySku(sku).getId());
        ResponseEntity.ok().body("Product with sku: " + sku + " has been deleted.");
    }

    @Override
    public void updateProduct(String sku, ProductDto productDto, BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException(bindingResult.toString());
        } else {
            Product existingProduct = productRepository.findBySku(sku);
            if (existingProduct == null) {
                throw new ApiRequestException("Product not found with sku: " + sku);
            } else {
                existingProduct.setSku(productDto.getSku());
                existingProduct.setProductTitle(productDto.getProductTitle());
                existingProduct.setProductDescription(productDto.getProductDescription());
                existingProduct.setProductPrice(productDto.getProductPrice());
                existingProduct.setEnabled(productDto.isEnabled());
                productRepository.save(existingProduct);
                ResponseEntity.ok().body("Product with sku: " + sku + " has been updated");
            }
        }
    }

    @Override
    public void createProduct(ProductDto productDto, BindingResult bindingResult) throws ParseException {
        // Validate the input before attempting to create a new product
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException(bindingResult.toString());
        } else if (duplicateProductCheck.checkDuplicateProduct(productDto.getSku())) {
            throw new ApiRequestException("Product with this sku already exists.");
        } else {
            Product product = new Product();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date((System.currentTimeMillis()));
            String currentDate = format.format(date);
            Date createdAtDate = format.parse(currentDate);

            product.setSku(productDto.getSku());
            product.setProductTitle(productDto.getProductTitle());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductPrice(productDto.getProductPrice());
            product.setCreatedAtDate(createdAtDate);
            product.setEnabled(productDto.isEnabled());
            productRepository.save(product);
            ResponseEntity.status(HttpStatus.CREATED).body(product);
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
            throw new ApiRequestException("Product export could not be completed " + exception.getMessage());
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
                String date = (csvRecord.get("createdAtDate"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date createdDate = formatter.parse(date);
                System.out.println(date);
                Product product = new Product(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("sku"),
                        csvRecord.get("productTitle"),
                        csvRecord.get("productDescription"),
                        Double.parseDouble(csvRecord.get("productPrice")),
                        Integer.parseInt(csvRecord.get("taxPercentage")),
                        createdDate,
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
