package johan.spekman.novibeie.module_product.product.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import johan.spekman.novibeie.module_category.model.Category;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sku;
    private String productTitle;
    private String productDescription;
    private double productPrice;
    private Date createdAtDate;
    private boolean enabled;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductMedia> productMediaList = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "catalog_category_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("productList")
    private List<Category> categories = new ArrayList<>();

    public Product() {
    }

    public Product(Long id, String sku, String productTitle, String productDescription,
                   double productPrice, Date createdAtDate,
                   boolean enabled) {
        this.id = id;
        this.sku = sku;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
