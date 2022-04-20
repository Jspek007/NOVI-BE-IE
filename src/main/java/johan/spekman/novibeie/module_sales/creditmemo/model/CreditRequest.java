package johan.spekman.novibeie.module_sales.creditmemo.model;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_sales.SalesActionRequest;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;

public class CreditRequest extends SalesActionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "customer_entity_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "sales_order_entity_id")
    private SalesOrder salesOrder;
}
