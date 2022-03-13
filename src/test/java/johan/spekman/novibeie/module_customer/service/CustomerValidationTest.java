package johan.spekman.novibeie.module_customer.service;

import johan.spekman.novibeie.NoviBeIeApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = NoviBeIeApplication.class)
class CustomerValidationTest {

    @Test
    void shouldReturnFalseOnCustomerPhoneNumberCheck() {
        String phonenumber = "12345";

        boolean expected = false;
        boolean result = CustomerValidation.checkCustomerPhoneNumber(phonenumber);

        assertEquals(expected, result);
    }

    @Test
    void shouldReturnTrueOnCustomerPhoneNumberCheck() {
        String phonenumber = "+31612345678";

        boolean expected = true;
        boolean result = CustomerValidation.checkCustomerPhoneNumber(phonenumber);

        assertEquals(expected, result);
    }
}