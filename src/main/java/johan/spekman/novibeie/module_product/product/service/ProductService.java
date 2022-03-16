package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

public interface ProductService {
    ResponseEntity<Object> createProduct(@Valid ProductDto productDto, BindingResult bindingResult);
    List<Product> getAllProducts();
}