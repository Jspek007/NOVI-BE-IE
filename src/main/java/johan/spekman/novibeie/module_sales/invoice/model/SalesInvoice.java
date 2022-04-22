package johan.spekman.novibeie.module_sales.invoice.model;

import johan.spekman.novibeie.module_sales.SalesResource;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;

@Entity
@Table(name = "sales_invoices")
public class SalesInvoice extends SalesResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "sales_order_entity_id")
    private SalesOrder salesOrder;
    private double grandTotal;

    public SalesInvoice() {
        super();
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
