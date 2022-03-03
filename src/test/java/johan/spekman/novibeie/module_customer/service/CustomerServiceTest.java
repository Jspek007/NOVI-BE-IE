package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.NoviBeIeApplication;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = NoviBeIeApplication.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Mock
    Customer customer;

    @Test
    public void supposedToGetAllCustomers() {
        customer = new Customer(
                null,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123"
        );
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        Mockito.when(customerRepository.findAll())
                .thenReturn(customers);

        String expected = "Henk";

        List<Customer> customerList = customerService.getAllCustomers();
        String found = customerList.get(0).getFirstName();
        assertEquals(expected, found);

    }

    @Test
    public void supposedToGetCustomerByEmailAddress() {
        customer = new Customer(
                null,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123"
        );

        Mockito.when(customerRepository.findByEmailAddress(customer.getEmailAddress()))
                .thenReturn(customer);

        String email = "Test@test.nl";
        String expected = "Test@test.nl";

        Customer found = customerService.getCustomerByEmailAddress(email);
        assertEquals(expected, found.getEmailAddress());

    }
}