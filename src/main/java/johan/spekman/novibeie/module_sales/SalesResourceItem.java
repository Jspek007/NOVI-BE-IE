package johan.spekman.novibeie.module_sales;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SalesResourceItem {
    private String sku;
    private String productTitle;
    private double productPrice;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}