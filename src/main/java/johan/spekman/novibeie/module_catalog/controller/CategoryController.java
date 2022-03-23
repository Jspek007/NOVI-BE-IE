package johan.spekman.novibeie.module_catalog.controller;

import johan.spekman.novibeie.module_catalog.dto.CategoryDto;
import johan.spekman.novibeie.module_catalog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping(path = "/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Object> createNewCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                    BindingResult bindingResult) {
        URI uri =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/category/save").toUriString());
        return ResponseEntity.created(uri).body(categoryService.createCategory(categoryDto, bindingResult));
    }

    @PostMapping(path = "/products/save/{categoryId}")
    public ResponseEntity<Object> addProductsToCategory(@PathVariable("categoryId") Long categoryId,
                                                        @RequestBody String[] skus) throws IOException {
        categoryService.addProductsToCategory(categoryId, skus);
        return ResponseEntity.status(HttpStatus.OK).body("Category has been updated! " + categoryId + " " + Arrays.toString(skus));
    }
}
