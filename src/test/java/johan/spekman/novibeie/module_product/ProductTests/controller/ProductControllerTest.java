package johan.spekman.novibeie.module_product.ProductTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import johan.spekman.novibeie.module_product.product.controller.ProductController;
import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Mock
    private BindingResult bindingResult;

    @Test
    void shouldGetAllProducts() throws Exception {
        Product product = new Product();
        product.setId(2L);
        product.setProductPrice(11);
        product.setProductTitle("Test product");
        product.setCreatedAtDate(new Date(System.currentTimeMillis()));
        product.setSku("sku_123456");

        Product product1 = new Product();
        product1.setProductDescription("Test product");
        product1.setId(1L);
        product1.setTaxPercentage(21);
        product1.setEnabled(true);
        List<Product> productList = List.of(product, product1);

        given(productService.getAllProducts()).willReturn(productList);

        mockMvc.perform(get("/api/v1/products/get/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].taxPercentage", is(21)))
                .andExpect(jsonPath("$[0].productTitle", is("Test product")));
    }

    @Test
    void shouldReturnErrorOnProductCreation() throws Exception {
        String uri = "/api/v1/products/save";
        Product product = new Product();
        product.setId(1L);
        product.setProductDescription("Dit is een test product");
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateNewProduct() throws Exception {
        String uri = "/api/v1/products/save";
        ProductDto productDto = new ProductDto();
        productDto.setSku("sku_123456");
        productDto.setProductTitle("Test product");
        productDto.setProductDescription("Dit is een test product");
        productDto.setProductPrice(11.99);
        productDto.setTaxPercentage(21);
        productDto.setEnabled(false);
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated());
    }
}
