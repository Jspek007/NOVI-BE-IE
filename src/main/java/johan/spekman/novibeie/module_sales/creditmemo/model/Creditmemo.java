package johan.spekman.novibeie.module_sales.creditmemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import johan.spekman.novibeie.module_sales.SalesResource;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales_creditmemo")
public class Creditmemo extends SalesResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    private Long entityId;
    @OneToOne
    @JsonIgnore
    private SalesOrder salesOrder;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditmemo_entity_id")
    @JsonIgnoreProperties({"id", "orderId", "orderItem", "product", "customer", "productPrice"})
    List<CreditmemoItem> creditmemoItemList;
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

    public List<CreditmemoItem> getCreditmemoItemList() {
        return creditmemoItemList;
    }

    public void setCreditmemoItemList(List<CreditmemoItem> creditmemoItemList) {
        this.creditmemoItemList = creditmemoItemList;
    }
}
