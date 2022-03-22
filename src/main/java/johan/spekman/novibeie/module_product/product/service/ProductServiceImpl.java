package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
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
    public ResponseEntity<Object> createProduct(ProductDto productDto, BindingResult bindingResult) {
        // Validate the input before attempting to create a new product
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputValidation.validate(bindingResult));
        } else {
            Product product = new Product();

            product.setSku(productDto.getSku());
            product.setProductTitle(productDto.getProductTitle());
            product.setProductDescription(productDto.getProductDescription());
            product.setProductPrice(productDto.getProductPrice());
            product.setCreatedAtDate(new Date(System.currentTimeMillis()));
            product.setEnabled(productDto.isEnabled());

            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }
    }
}
