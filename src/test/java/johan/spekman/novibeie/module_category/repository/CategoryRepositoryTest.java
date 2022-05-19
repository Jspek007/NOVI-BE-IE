package johan.spekman.novibeie.module_category.repository;

import johan.spekman.novibeie.module_category.model.Category;
import johan.spekman.novibeie.module_category.service.CategoryServiceImpl;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@DataJpaTest
public class CategoryRepositoryTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;

    @MockBean
    private CategoryServiceImpl categoryService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, productRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldGetAllCategories() {
        categoryService.getCategories();
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldGetSpecificCategoryById() {
        Category category = new Category();
        category.setId(1L);

        categoryService.getSpecificCategory(category.getId());

        verify(categoryRepository).getById(category.getId());
    }
}
