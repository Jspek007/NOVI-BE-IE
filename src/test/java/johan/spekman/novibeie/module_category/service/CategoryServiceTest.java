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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
public class CategoryServiceTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InputValidation inputValidation;

    @MockBean
    private CategoryServiceImpl underTest;
    private AutoCloseable autoCloseable;

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
        CategoryDto categoryDto = new CategoryDto(
                "Test category",
                "This is a test category"
        );
        BindingResult bindingResult = new BindException(categoryDto, "category");

        underTest.createCategory(categoryDto, bindingResult);

        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);

        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertThat(capturedCategory.getCategoryName()).isEqualTo(categoryDto.getCategoryName());

    }

    @Test
    void shouldAddProductsToCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test category");
        category.setCategoryDescription("This is a test category");
        category.setProductList(new ArrayList<>());
        Product product = new Product();
        product.setSku("sku_123456");
        String[] skus = {"sku_123456"};
        productRepository.save(product);
        categoryRepository.save(category);

        underTest.addProductsToCategory(1L, skus);

        Category capturedCategory = categoryRepository.getById(1L);

        assertThat(capturedCategory.getProductList().get(0).getSku()).isEqualTo(product.getSku());
    }

    @Test
    void shouldReturnException_productNotFound() {
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Test category");
        category.setCategoryDescription("This is a test category");
        category.setProductList(new ArrayList<>());
        String[] skus = {"sku_123456"};
        categoryRepository.save(category);

        assertThrows(ApiRequestException.class, () -> underTest.addProductsToCategory(1L, skus));
    }

    @Test
    void removeProductFromCategory() {
        Category category = new Category();
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        Product product1 = new Product();
        product.setSku("sku_123456");
        product1.setSku("sku_123457");
        productList.add(product);
        productList.add(product1);
        category.setProductList(productList);
        categoryRepository.save(category);

        String[] skus = {"sku_123456"};

        underTest.removeProductFromCategory(1L, skus);

        Category capturedCategory = categoryRepository.getById(1L);
        assertThat(capturedCategory.getProductList().get(0).getSku()).isEqualTo(product1.getSku());

    }
}
