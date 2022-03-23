package johan.spekman.novibeie.module_product.product.controller;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
                                                BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/products/save").toUriString());
            return ResponseEntity.created(uri).body(productService.createProduct(productDto, bindingResult));
        } else {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError());
        }
    }
}
