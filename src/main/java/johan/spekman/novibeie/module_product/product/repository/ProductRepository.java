package johan.spekman.novibeie.module_product.product.repository;

import johan.spekman.novibeie.module_product.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySku(String sku);
}
