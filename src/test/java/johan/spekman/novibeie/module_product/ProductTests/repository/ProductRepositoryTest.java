package johan.spekman.novibeie.module_product.ProductTests.repository;

import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    final Date date = new Date(System.currentTimeMillis());

    @Test
    void shouldFindProductBySku() {
        Product product = new Product(
                null,
                "sku_123456",
                "Test product",
                "This is a test product",
                11.99,
                9,
                date,
                false
        );
        productRepository.save(product);

        Product found = productRepository.findBySku("sku_123456");

        String expected = "This is a test product";
        String actual = found.getProductDescription();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnError() {
        Product product = new Product(
                null,
                "sku_123456",
                "Test product",
                "This is a test product",
                11.99,
                9,
                date,
                false
        );

        productRepository.save(product);

        Product actual = productRepository.findBySku("123456");

        assertEquals(null, actual);
    }
}
