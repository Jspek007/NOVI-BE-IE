package johan.spekman.novibeie.module_sales.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales_invoices")
public class SalesInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "customer_entity_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "sales_order_entity_id")
    private SalesOrder salesOrder;
    private Date createdAtDate;
    private double grandTotal;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddress shippingAddress;
    @JsonIgnore
    @ManyToOne
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

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
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
