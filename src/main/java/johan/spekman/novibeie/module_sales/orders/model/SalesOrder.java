package johan.spekman.novibeie.module_sales.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales_orders")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private Long entityId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_entity_id")
    @JsonIgnoreProperties("password")
    private Customer customer;
    @Column(name = "created_at_date")
    private Date createdAtDate;
    @JsonIgnoreProperties("productPrice")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_entity_id")
    private List<SalesOrderItem> orderItemList;
    @Column(name = "sales_order_items_total")
    private int totalItems;
    @Column(name = "grand_total")
    private double grandTotal;
    private double amountPaid;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddress shippingAddress;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    private CustomerAddress billingAddress;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
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

    public List<SalesOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<SalesOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
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
