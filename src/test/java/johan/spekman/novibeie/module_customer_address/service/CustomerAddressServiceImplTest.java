package johan.spekman.novibeie.module_customer_address.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.dto.CustomerAddressDto;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
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

import static org.mockito.Mockito.verify;


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
        Customer customer = new Customer(
                100L,
                111111L,
                "Test",
                "",
                "Tester",
                "+3112345678",
                "Test@test.com",
                "Test123!"
        );
        customerRepository.save(customer);

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

        customerRepository.findByCustomerId(111111L);

        // When
        underTest.createNewAddress(customerAddressDto, bindingResult);

        // Then
        ArgumentCaptor<CustomerAddress> customerAddressArgumentCaptor = ArgumentCaptor.forClass(CustomerAddress.class);
        verify(customerAddressArgumentCaptor).toString();
    }
}