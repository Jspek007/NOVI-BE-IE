package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CustomerAddressServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerAddressRepository customerAddressRepository;

    @MockBean
    private CustomerAddressServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerAddressServiceImpl(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldThrowInvalidAddressException() {
        // Given
        CustomerAddressDto customerAddressDto = new CustomerAddressDto(
                111111L,
                "Teststreet",
                12,
                "A6",
                "1111",
                "Testcity",
                CustomerAddressType.billing);
        BindingResult bindingResult = new BindException(customerAddressDto, "customerAddress");

        Throwable exception = assertThrows(ApiRequestException.class,
                () -> underTest.createNewAddress(customerAddressDto, bindingResult));

        assertEquals("Postal code is not valid", exception.getMessage());
    }

    @Test
    void shouldCreateCustomerAddress() {
        Customer customer = new Customer(
                1L,
                111111L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "encryptedPassword");
        customerRepository.save(customer);
        CustomerAddressDto customerAddressDto = new CustomerAddressDto(
                111111L,
                "Teststreet",
                12,
                "A6",
                "1111AA",
                "Testcity",
                CustomerAddressType.billing);
        BindingResult bindingResult = new BindException(customerAddressDto, "customerAddress");

        underTest.createNewAddress(customerAddressDto, bindingResult);
        // Then
        ArgumentCaptor<CustomerAddress> customerArgumentCaptor = ArgumentCaptor.forClass(CustomerAddress.class);
        verify(customerAddressRepository).save(customerArgumentCaptor.capture());

        CustomerAddress capturedAddress = customerArgumentCaptor.getValue();
        assertThat(capturedAddress.getPostalCode()).isEqualTo(customerAddressDto.getPostalCode());
    }
}