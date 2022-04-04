package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public record DuplicateProductCheck(ProductRepository productRepository) {

    public boolean checkDuplicateProduct(String sku) {
        return productRepository.findBySku(sku) != null;
    }
}
