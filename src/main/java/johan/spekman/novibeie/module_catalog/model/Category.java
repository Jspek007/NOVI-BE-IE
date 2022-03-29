package johan.spekman.novibeie.module_catalog.model;

import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "entity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    private String categoryDescription;

    @ManyToMany(
            mappedBy = "categories",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    private List<Product> productList = new ArrayList<Product>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}