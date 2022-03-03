package johan.spekman.novibeie.module_customer.model;

import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    @Test
    void shouldReturnEmptyListOfAddresses() {

        // Arrange
        Customer customer = new Customer();

        // Act
        customer.addCustomerAddress(null);

        // Assert
        List<Object> expected = new ArrayList<>();
        List<CustomerAddress> actual = customer.getCustomerAddresses();
        assertEquals(expected, actual);
    }
}