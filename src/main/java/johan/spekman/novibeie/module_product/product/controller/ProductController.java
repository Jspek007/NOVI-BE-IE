package johan.spekman.novibeie.module_product.product.controller;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/save")
    private ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDto productDto,
                                                 BindingResult bindingResult) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/v1/products/save").toUriString());
        return ResponseEntity.created(uri).body(productService.createProduct(productDto, bindingResult));
    }
}
