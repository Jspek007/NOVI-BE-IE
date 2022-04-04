package johan.spekman.novibeie.module_catalog.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDto {
    @NotBlank
    private String categoryName;

    @NotBlank
    private String categoryDescription;

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
