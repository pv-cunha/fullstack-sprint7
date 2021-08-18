package br.com.rchlo.store.dto;

import br.com.rchlo.store.domain.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDto {

    private String name;
    private String slug;

    public CategoryDto(Category category) {
        this.name = category.getName();
        this.slug = category.getSlug();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public static List<CategoryDto> convert(List<Category> categoryList) {
        return categoryList.stream().map(CategoryDto::new).collect(Collectors.toList());
    }
}
