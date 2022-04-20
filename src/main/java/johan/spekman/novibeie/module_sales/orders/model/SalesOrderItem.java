package johan.spekman.novibeie.module_sales.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_product.product.model.Product;

import javax.persistence.*;

@Entity
@Table(name = "sales_order_items")
public class SalesOrderItem {
    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_entity_id")
    private SalesOrder orderId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_item_entity_id")
    @JsonIgnoreProperties({"productMediaList", "categories", "createdAtDate", "enabled", "productDescription"})
    private Product orderItem;
    private String sku;
    private double productPrice;

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

    public Product getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Product orderItem) {
        this.orderItem = orderItem;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
