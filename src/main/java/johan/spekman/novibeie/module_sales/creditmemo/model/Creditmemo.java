package johan.spekman.novibeie.module_sales.creditmemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import johan.spekman.novibeie.module_sales.SalesResource;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;

@Entity
@Table(name = "sales_creditmemo")
public class Creditmemo extends SalesResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "sales_order_entity_id")
    @JsonIgnore
    private SalesOrder salesOrder;
    private double amountRefunded;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public double getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(double amountRefunded) {
        this.amountRefunded = amountRefunded;
    }
}
