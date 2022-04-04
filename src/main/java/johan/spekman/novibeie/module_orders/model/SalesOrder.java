package johan.spekman.novibeie.module_orders.model;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_product.product.model.Product;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales_orders")
public class SalesOrder {
    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_entity_id")
    private Customer customer;
    private Date createdAtDate;
    @ManyToOne
    @JoinColumn(name = "order_item_entity_id")
    private Product orderItem;
    private double productPrice;
    private double grandTotal;
    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddress shippingAddress;
    @ManyToOne
    @JoinColumn(name = "billing_address_id")
    private CustomerAddress billingAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public Product getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Product orderItem) {
        this.orderItem = orderItem;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public CustomerAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(CustomerAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public CustomerAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(CustomerAddress billingAddress) {
        this.billingAddress = billingAddress;
    }
}
