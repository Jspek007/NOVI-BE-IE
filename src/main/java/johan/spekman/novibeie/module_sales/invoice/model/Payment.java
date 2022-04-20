package johan.spekman.novibeie.module_sales.invoice.model;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;

@Entity
@Table(name = "sales_order_payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    private double paymentAmount;
    @OneToOne
    private Customer customer;
    @OneToOne
    private SalesOrder salesOrder;

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
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
