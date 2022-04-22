package johan.spekman.novibeie.module_sales.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_sales.SalesResource;
import johan.spekman.novibeie.module_sales.SalesResourceItem;

import javax.persistence.*;

@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem extends SalesResourceItem {
    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_entity_id")
    private SalesOrder orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalesOrder getOrderId() {
        return orderId;
    }

    public void setOrderId(SalesOrder orderId) {
        this.orderId = orderId;
    }
}
