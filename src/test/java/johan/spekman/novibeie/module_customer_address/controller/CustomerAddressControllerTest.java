package johan.spekman.novibeie.module_customer_address.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import johan.spekman.novibeie.module_customer_address.service.CustomerAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = CustomerAddressController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerAddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CustomerAddressService customerAddressService;

    @Test
    void canSaveNewCustomerAddress() throws Exception {
        String uri = "/api/v1/customer/address/save";
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(123456L);
        customerAddress.setStreetName("Teststreet");
        customerAddress.setAddition("A6");
        customerAddress.setCity("Testcity");
        customerAddress.setPostalCode("1111TE");
        customerAddress.setCustomerAddressType(CustomerAddressType.billing);
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerAddress)))
                .andExpect(status().isCreated());
    }
}