package johan.spekman.novibeie.module_product.product.controller;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.service.ProductService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final CSVFormatCheck csvFormatCheck;

    public ProductController(ProductService productService, CSVFormatCheck csvFormatCheck) {
        this.productService = productService;
        this.csvFormatCheck = csvFormatCheck;
    }

    @GetMapping(path = "/get/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/get/{sku}")
    public Product getProductBySku(@PathVariable("sku") String sku) {
        return productService.getProductBySku(sku);
    }

    @DeleteMapping(path = "/delete/{sku}")
    public ResponseEntity<String> deleteProductBySku(@PathVariable("sku") String sku) {
        productService.deleteProductBySku(sku);
        return ResponseEntity.ok("Product with sku: " + sku + " had been deleted.");
    }

    @PutMapping(path = "/update/{sku}")
    public ResponseEntity<Object> updateProduct(@PathVariable("sku") String sku,
                                                @Valid @RequestBody ProductDto productDto,
                                                BindingResult bindingResult) {
        ResponseEntity<Object> product = productService.updateProduct(sku, productDto, bindingResult);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(product);
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDto productDto,
                                                BindingResult bindingResult) throws ParseException {
        if (!bindingResult.hasErrors()) {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/products/save").toUriString());
            return ResponseEntity.created(uri).body(productService.createProduct(productDto, bindingResult));
        } else {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
        }
    }

    @GetMapping(path = "/export/all", produces = "text/csv")
    public void productExport(HttpServletResponse response) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        /*
            write actual data to CSV file
         */
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"products" + format.format(date) + ".csv\"");
        productService.productExport(response.getWriter());
    }

    @PostMapping(path = "/import")
    public ResponseEntity<Object> productImport(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (!csvFormatCheck.hasCSVFormat(file)) {
            message = "Please upload a csv file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        } else {
            try {
                productService.saveAll(file);
                message = "Uploaded the file succesfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception exception) {
                message = "Could not upload the file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
    }
}
