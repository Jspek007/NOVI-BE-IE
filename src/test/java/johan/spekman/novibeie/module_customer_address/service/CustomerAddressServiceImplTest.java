package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;



@SpringBootTest
class CustomerAddressServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

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
                CustomerAddressType.billing
        );
        BindingResult bindingResult = new BindException(customerAddressDto, "customerAddress");

        // When
        String result = String.valueOf(underTest.createNewAddress(customerAddressDto, bindingResult));

        // Then
        assertThat(result.toLowerCase(Locale.ROOT)).isEqualTo("<400 bad_request bad request,incorrect postal code format,[]>");
    }
}