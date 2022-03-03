package johan.spekman.novibeie.module_customer.repository;

import static org.junit.jupiter.api.Assertions.*;

import johan.spekman.novibeie.module_customer.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldFindCustomerById() {
        // given
        Customer customer = new Customer(
                null,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123"
        );
        underTest.save(customer);

        // when
        Customer found = underTest.findByCustomerId(123456L);

        // then
        String expected = "Henk" + "de" + "Tester";
        String actual = found.getFirstName() + found.getInsertion() + found.getLastName();

        assertEquals(expected, actual);
    }
}