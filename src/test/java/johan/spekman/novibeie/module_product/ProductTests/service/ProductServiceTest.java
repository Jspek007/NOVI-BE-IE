package johan.spekman.novibeie.module_product.ProductTests.service;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_product.product.service.DuplicateProductCheck;
import johan.spekman.novibeie.module_product.product.service.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @MockBean
    private ProductServiceImpl productService;
    @MockBean
    private DuplicateProductCheck productCheck;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository, productCheck);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldGetAllProducts() {
        productService.getAllProducts();
        verify(productRepository).findAll();
    }

    @Test
    void shouldCreateNewProduct() throws ParseException {
        ProductDto productDto = new ProductDto(
                "sku_111111",
                "Test product",
                "This is a test product",
                25.99,
                21,
                true
        );
        BindingResult bindingResult = new BindException(productDto, "product");
        productService.createProduct(productDto, bindingResult);

        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct.isEnabled()).isTrue();
        assertThat(capturedProduct.getProductDescription()).isEqualTo(productDto.getProductDescription());
    }
}
