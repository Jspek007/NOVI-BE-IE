package johan.spekman.novibeie.module_category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import johan.spekman.novibeie.module_category.model.Category;
import johan.spekman.novibeie.module_category.repository.CategoryRepository;
import johan.spekman.novibeie.module_category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryService categoryService;

    @Mock
    Category category;

    @Test
    void canGetAllCategoriesWhenExists() {
        // given
        given(categoryRepository.findAll())
                .willReturn(new ArrayList<>());
    }

    @Test
    void willReturnSpecificCategoryWhenExists() {
        // given
        given(categoryRepository.getById(1L))
                .willReturn(new Category(1L, "Test category", "This is a test category", new ArrayList<>()));
    }

    @Test
    void whenValidInput_CreateNewCategory_andReturn200Ok() throws Exception {
        mockMvc.perform(post("/api/v1/categories/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInvalidInput_returnStatus400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/categories/save")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidInput_shouldAddProductsToCategory() throws Exception {
        String[] products = {"sku_123456"};
        mockMvc.perform(post("/api/v1/categories/products/save/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(products)))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidInput_shouldReturnStatus400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/categories/products/save/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
