package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.NoviBeIeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = NoviBeIeApplication.class)
class CustomerValidationTest {

    @Test
    void shouldReturnFalseOnCustomerPhoneNumberCheck() {
        String phoneNumber = "12345";

        boolean expected = false;
        boolean result = CustomerValidation.checkCustomerPhoneNumber(phoneNumber);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnTrueOnCustomerPhoneNumberCheck() {
        String phoneNumber = "+31612345678";

        boolean expected = true;
        boolean result = CustomerValidation.checkCustomerPhoneNumber(phoneNumber);

        assertEquals(expected, result);
    }
}