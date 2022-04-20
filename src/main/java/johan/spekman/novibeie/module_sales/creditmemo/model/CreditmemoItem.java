package johan.spekman.novibeie.module_sales.creditmemo.model;

import johan.spekman.novibeie.module_sales.SalesResourceItem;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;

@Entity
@Table(name = "sales_creditmemo_items")
public class CreditmemoItem extends SalesResourceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    private String sku;
    private double itemPrice;
    @ManyToOne
    @JoinColumn(name = "sales_order_entity_id")
    private SalesOrder salesOrder;

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }
}
