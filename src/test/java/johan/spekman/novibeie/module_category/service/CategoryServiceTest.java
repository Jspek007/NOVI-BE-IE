package johan.spekman.novibeie.module_category.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_category.dto.CategoryDto;
import johan.spekman.novibeie.module_category.model.Category;
import johan.spekman.novibeie.module_category.repository.CategoryRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl underTest;

    @Mock
    private Category category;

    @Mock
    InputValidation inputValidation;

    @Mock
    BindingResult bindingResult;

    @MockBean
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CategoryServiceImpl(categoryRepository, productRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldCreateNewCategory() {
        when(inputValidation.validate(bindingResult)).thenReturn(null);

        CategoryDto categoryDto = new CategoryDto(
                "Test category",
                "This is a test category"
        );

        ResponseEntity<Object> storedCategory = underTest.createCategory(categoryDto, bindingResult);
        assertThat(storedCategory.getBody()).isEqualTo("Category has been created!");
    }

    @Test
    void shouldAddProductsToCategory() {
        Category category = new Category(
                1L,
                "Test category to add products",
                "This category will have products added",
                new ArrayList<>()
        );
        Product product = new Product();
        product.setSku("sku_123456");
        String[] skus = {"sku_123456"};

        when(categoryRepository.getById(1L)).thenReturn(category);
        when(productRepository.findBySku(anyString())).thenReturn(product);

        underTest.addProductsToCategory(1L, skus);

        assertThat(product.getCategories().get(0).getCategoryName()).isEqualTo(category.getCategoryName());

    }

    @Test
    void shouldReturnException_productNotFound() {
        String[] skus = {"sku_123456"};
        categoryRepository.save(category);

        assertThrows(ApiRequestException.class, () -> underTest.addProductsToCategory(1L, skus));
    }

    @Test
    void removeProductFromCategory() {
        Category category = new Category(
                1L,
                "Test category to add products",
                "This category will have products added",
                new ArrayList<>()
        );
        List<Product> productList = new ArrayList<>();
        category.setProductList(productList);
        Product product = new Product();
        Product product1 = new Product();
        product.setSku("sku_123456");
        product1.setSku("sku_123457");
        productList.add(product);
        productList.add(product1);
        categoryRepository.save(category);

        String[] skus = {"sku_123456"};

        when(categoryRepository.getById(1L)).thenReturn(category);
        when(productRepository.findBySku(anyString())).thenReturn(product);

        underTest.removeProductFromCategory(1L, skus);

        Category capturedCategory = categoryRepository.getById(1L);
        assertThat(capturedCategory.getProductList().get(0).getSku()).isEqualTo(product1.getSku());

    }
}
