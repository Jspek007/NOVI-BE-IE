package johan.spekman.novibeie.module_category.service;

import johan.spekman.novibeie.module_category.dto.CategoryDto;
import johan.spekman.novibeie.module_category.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getSpecificCategory(Long categoryId);
    ResponseEntity<Object> createCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult);
    ResponseEntity<Object> addProductsToCategory(Long categoryId, String[] skus) throws IOException;
    void removeProductFromCategory(Long categoryId, String[] skus);
}
