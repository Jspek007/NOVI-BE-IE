package johan.spekman.novibeie.module_catalog.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_catalog.dto.CategoryDto;
import johan.spekman.novibeie.module_catalog.model.Category;
import johan.spekman.novibeie.module_catalog.repository.CategoryRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getSpecificCategory(Long categoryId) {
        return categoryRepository.getById(categoryId);
    }

    @Override
    public ResponseEntity<Object> createCategory(CategoryDto categoryDto, BindingResult bindingResult) {
        InputValidation inputValidation = new InputValidation();
        if (inputValidation.validate(bindingResult) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(inputValidation.validate(bindingResult));
        } else {
            Category category = new Category();

            category.setCategoryName(categoryDto.getCategoryName());
            category.setCategoryDescription(categoryDto.getCategoryDescription());

            categoryRepository.save(category);
            return new ResponseEntity<>("Category has been created!", HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object> addProductsToCategory(Long categoryId, String[] skus) {
        Category category = categoryRepository.getById(categoryId);

        try {
            Arrays.stream(skus).forEach(sku -> {
                if (productRepository.findBySku(sku) == null) {
                    throw new ApiRequestException("No product found with sku: " + sku);
                } else {
                    Product product = productRepository.findBySku(sku);
                    /*
                        Check if the category is already linked to one of the products
                        If yes, skip that sku and continue on the next
                    */
                    if (category.getProductList().contains(product)) {
                        return;
                    }
                    product.getCategories().add(category);
                    productRepository.save(product);
                }
            });
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Category " + category.getCategoryName() + " has been " +
                "updated: " + Arrays.toString(skus));
    }

    @Override
    public void removeProductFromCategory(Long categoryId, String[] skus) {
        Category category = categoryRepository.getById(categoryId);

        try {
            Arrays.stream(skus).forEach(sku -> {
                Product product = productRepository.findBySku(sku);
                product.getCategories().remove(category);
            });
        } catch (Exception exception) {
            throw new ApiRequestException("Products could not be removed from the category" + exception.getMessage());
        }
    }
}
