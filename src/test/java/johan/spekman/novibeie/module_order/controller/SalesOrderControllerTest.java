package johan.spekman.novibeie.module_order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import johan.spekman.novibeie.module_sales.orders.controller.SalesOrderController;
import johan.spekman.novibeie.module_sales.orders.service.SalesOrderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = SalesOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SalesOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private SalesOrderService salesOrderService;


    @Mock
    SalesOrder salesOrder;


    @Test
    public void whenValidInput_thenReturn200() throws Exception {
        mockMvc.perform(post("/api/v1/sales_orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salesOrder)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidInput_thenReturn400() throws Exception {
        mockMvc.perform(post("/api/v1/sales_orders/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
