package johan.spekman.novibeie.module_sales.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import johan.spekman.novibeie.module_sales.SalesResource;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sales_orders")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SalesOrder extends SalesResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private Long entityId;
    @JsonIgnoreProperties("productPrice")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_entity_id")
    private List<SalesOrderItem> orderItemList;
    @Column(name = "sales_order_items_total")
    private int totalItems;
    @Column(name = "grand_total")
    private double grandTotal;
    private double amountPaid;
    private double amountRefunded;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
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

    public double getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(double amountRefunded) {
        this.amountRefunded = amountRefunded;
    }
}
