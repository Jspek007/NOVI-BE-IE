package johan.spekman.novibeie.module_customer.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import johan.spekman.novibeie.module_customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

    @Mock
    private CustomerRepository underTest;

    @Test
    void findByCustomerId() {
        // given
        Customer customer = new Customer(
                null,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        underTest.save(customer);

        // when
        when(underTest.findByCustomerId(123456L)).thenReturn(customer);
        Customer found = underTest.findByCustomerId(123456L);

        // then
        String expected = "Henk" + "de" + "Tester";
        String actual = found.getFirstName() + found.getInsertion() + found.getLastName();

        assertEquals(expected, actual);
    }

    @Test
    void findByEmailAddress() {
        Customer customer = new Customer(
                null,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        underTest.save(customer);

        // when
        when(underTest.findByEmailAddress("Test@test.nl")).thenReturn(customer);
        Customer found = underTest.findByEmailAddress("Test@test.nl");

        // then
        String expected = "Test@test.nl";
        String actual = found.getEmailAddress();

        assertEquals(expected, actual);
    }
}