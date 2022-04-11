package johan.spekman.novibeie.module_orders.dto;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_product.product.model.Product;

import java.util.List;

public record SalesOrderItemDto(Customer customer,
                                List<Product> products) {

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }
}
