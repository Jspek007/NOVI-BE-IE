package johan.spekman.novibeie.module_category.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDto {
    @NotBlank
    private final String categoryName;

    @NotBlank
    private final String categoryDescription;

    public CategoryDto(String categoryName, String categoryDescription) {
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }
}
