package johan.spekman.novibeie.module_order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import johan.spekman.novibeie.module_orders.controller.SalesOrderController;
import johan.spekman.novibeie.module_orders.service.SalesOrderService;

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

}
