package johan.spekman.novibeie.module_catalog.service;

import johan.spekman.novibeie.module_catalog.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.io.IOException;

public interface CategoryService {
    ResponseEntity<Object> createCategory(@Valid CategoryDto categoryDto, BindingResult bindingResult);
    ResponseEntity<Object> addProductsToCategory(Long categoryId, String[] skus) throws IOException;
}
