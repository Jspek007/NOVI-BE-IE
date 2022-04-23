package johan.spekman.novibeie.module_sales.creditmemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import johan.spekman.novibeie.module_sales.creditmemo.service.CreditmemoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = CreditmemoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CreditmemoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CreditmemoService creditmemoService;

    final String[] skus = {"sku_123456"};


    @Test
    void correctRequest_shouldReturn201CreatedStatus() throws Exception {
        String uri = "/api/v1/sales_orders/creditmemo/process/{orderId}";

        mockMvc.perform(post(uri, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(skus)))
                .andExpect(status().isCreated());
    }
}
