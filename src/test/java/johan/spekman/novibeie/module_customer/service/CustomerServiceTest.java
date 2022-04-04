package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @MockBean
    private CustomerServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @MockBean
    private InputValidation inputValidation;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerServiceImpl(customerRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldGetAllCustomer() {
        // When
        underTest.getAllCustomers();
        // Then
        verify(customerRepository).findAll();
    }

    @Test
    void supposedToGetCustomerByEmailAddress() {
        // Given
        Customer customer = new Customer();
        customer.setEmailAddress("Test@test.com");
        // When
        underTest.getCustomerByEmailAddress(customer.getEmailAddress());
        // Then
        verify(customerRepository).findByEmailAddress(customer.getEmailAddress());
    }

    @Test
    void shouldCreateNewCustomer() {
        // Given
        String encryptedPassword = passwordEncoder.encode("Test123");
        CustomerDto customerDto = new CustomerDto(
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                encryptedPassword);
        BindingResult bindingResult = new BindException(customerDto, "customer");
        // When
        underTest.createCustomer(customerDto, bindingResult);
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getEmailAddress()).isEqualTo(customerDto.getEmailAddress());
    }
}