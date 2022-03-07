package johan.spekman.novibeie.module_customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    void canGetAllCustomers() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("Test");
        List<Customer> allCustomers = List.of(customer);

        given(customerService.getAllCustomers()).willReturn(allCustomers);

        mockMvc.perform(get("/api/v1/customers/get/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("Test")));
    }

    @Test
    void canGetCustomerByEmailAddress() throws Exception {
        Customer customer = new Customer();
        customer.setEmailAddress("Test@test.nl");

        given(customerService.getCustomerByEmailAddress(customer.getEmailAddress())).willReturn(customer);

        mockMvc.perform(get("/api/v1/customers/get/Test@test.nl")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailAddress", is("Test@test.nl")));
    }

    @Test
    void canSaveNewCustomer() throws Exception {
        String uri = "/api/v1/customers/save";
        Customer customer = new Customer();
        customer.setFirstName("Tester");
        customer.setLastName("Tester");
        customer.setPhoneNumber("+31612345678");
        customer.setEmailAddress("Test+12@Test.nl");
        customer.setPassword("Test123!");
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated());
    }
}