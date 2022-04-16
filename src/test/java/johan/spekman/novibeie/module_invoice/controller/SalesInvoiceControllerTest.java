package johan.spekman.novibeie.module_invoice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import johan.spekman.novibeie.module_invoice.model.Payment;
import johan.spekman.novibeie.module_invoice.service.SalesInvoiceService;
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
@ContextConfiguration(classes = SalesInvoiceController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SalesInvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Mock
    Payment payment;

    @MockBean
    private SalesInvoiceService salesInvoiceService;

    @Test
    void correctPayment_ShouldReturn200OkStatus() throws Exception {
        String uri = "/api/v1/sales_orders/payment/process/{orderId}";

        mockMvc.perform(post(uri, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk());
    }

    @Test
    void incorrectUri_ShouldReturn400BadRequestStatus() throws Exception {
        String uri = "/api/v1/sales_orders/payment/process/{orderId}";

        mockMvc.perform(post(uri, "g")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isBadRequest());
    }
}
