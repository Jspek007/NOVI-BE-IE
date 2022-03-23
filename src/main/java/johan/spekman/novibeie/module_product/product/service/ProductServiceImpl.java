package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
