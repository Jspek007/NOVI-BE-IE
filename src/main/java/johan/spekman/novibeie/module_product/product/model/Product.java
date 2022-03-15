package johan.spekman.novibeie.module_product.product.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "catalog_products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Product {
    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String productTitle;
    private String productDescription;
    private double productPrice;
    private int taxPercentage;
    private Date createdAtDate;
    private boolean enabled;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductMedia> productMediaList = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String sku, String productTitle, String productDescription,
                   double productPrice, int taxPercentage, Date createdAtDate,
                   boolean enabled) {
        this.id = id;
        this.sku = sku;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.taxPercentage = taxPercentage;
        this.createdAtDate = createdAtDate;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<ProductMedia> getProductMediaList() {
        return productMediaList;
    }

    public void setProductMediaList(List<ProductMedia> productMediaList) {
        this.productMediaList = productMediaList;
    }

    public void addProductMedia(ProductMedia productMedia) {
        if (productMedia != null) {
            if (productMediaList == null) {
                productMediaList = new ArrayList<>();
            }
            productMediaList.add(productMedia);
            productMedia.setProduct(this);
        }
    }
}
