package johan.spekman.novibeie.module_product.product.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
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
    public void deleteProductBySku(@PathVariable("sku") String sku) {
        productService.deleteProductBySku(sku);
    }

    @PutMapping(path = "/update/{sku}")
    public ResponseEntity<Product> updateProduct(@PathVariable("sku") String sku,
                                                @Valid @RequestBody ProductDto productDto,
                                                BindingResult bindingResult) {
        return ResponseEntity.ok().body(productService.updateProduct(sku, productDto, bindingResult));
    }

    @PostMapping(path = "/save")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto,
                                                 BindingResult bindingResult) throws ParseException {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/products/save").toUriString());
        return ResponseEntity.created(uri).body(productService.createProduct(productDto, bindingResult));
    }

    @GetMapping(path = "/export/all", produces = "text/csv")
    public void productExport(HttpServletResponse response) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"products" + format.format(date) + ".csv\"");
        productService.productExport(response.getWriter());
    }

    @PostMapping(path = "/import")
    public ResponseEntity<Object> productImport(@RequestParam("file") MultipartFile file) {
        String message;
        if (CSVFormatCheck.hasCSVFormat(file)) {
            message = "Please upload a csv file!";
            throw new ApiRequestException(message);
        } else {
            try {
                productService.saveAll(file);
                message = "Uploaded the file succesfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception exception) {
                throw new ApiRequestException("Could not upload the file. " + exception.getMessage());
            }
        }
    }
}
