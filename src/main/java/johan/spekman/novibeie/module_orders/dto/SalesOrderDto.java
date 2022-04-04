package johan.spekman.novibeie.module_orders.dto;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_product.product.model.Product;

import java.util.ArrayList;

public class SalesOrderDto {
    private final Customer customer;
    private final ArrayList<Product> products;

    public SalesOrderDto(Customer customer, ArrayList<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
