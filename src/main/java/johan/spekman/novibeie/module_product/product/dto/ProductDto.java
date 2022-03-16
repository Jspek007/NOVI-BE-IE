package johan.spekman.novibeie.module_product.product.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProductDto {
    @NotBlank
    @Pattern(regexp = "sku_[0-9]{6}")
    private String sku;

    @NotBlank
    @Size(min = 0, max = 50)
    private String productTitle;

    @NotBlank
    @Size(min = 0, max = 250)
    private String productDescription;

    @NotNull
    private double productPrice;

    @NotNull
    private int taxPercentage;

    private boolean enabled;

    public ProductDto(String sku, String productTitle, String productDescription, double productPrice, int taxPercentage, boolean enabled) {
        this.sku = sku;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.taxPercentage = taxPercentage;
        this.enabled = enabled;
    }

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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
