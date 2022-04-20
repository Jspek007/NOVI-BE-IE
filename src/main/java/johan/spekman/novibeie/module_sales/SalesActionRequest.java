package johan.spekman.novibeie.module_sales;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

public class SalesActionRequest {
    private Long entityId;
    private Customer customer;
    private SalesOrder salesOrder;

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
}
