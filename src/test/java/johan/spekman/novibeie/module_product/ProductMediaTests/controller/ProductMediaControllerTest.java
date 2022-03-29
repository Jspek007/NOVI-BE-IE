package johan.spekman.novibeie.module_product.ProductMediaTests.controller;

import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product_media.controller.ProductMediaController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = ProductMediaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductMediaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductMediaController productMediaController;

    @Test
    public void canSaveProductMedia() throws Exception {
        String uri = "/api/v1/products/media/gallery/upload/sku_123456";
        Product product = new Product();
        product.setSku("sku_123456");
        MockMultipartFile file = new MockMultipartFile(
                "image1",
                "image1.png",
                "image/png",
                "image".getBytes()
        );

        mockMvc.perform(multipart(uri)
                .file("file", file.getBytes()))
                .andExpect(status().isOk());
    }
}
