package johan.spekman.novibeie.module_orders.model;

import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales_orders")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private long entityId;
    @Column(name = "created_at_date")
    private Date createdAtDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_entity_id")
    private List<SalesOrderItem> orderItemList;
    @Column(name = "sales_order_items_total")
    private int totalItems;
    @Column(name = "grand_total")
    private double grandTotal;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddress shippingAddress;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    private CustomerAddress billingAddress;

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void setShippingAddress(CustomerAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setBillingAddress(CustomerAddress billingAddress) {
        this.billingAddress = billingAddress;
    }
}
