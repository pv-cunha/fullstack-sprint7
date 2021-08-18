package br.com.rchlo.store.form;

import br.com.rchlo.store.domain.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CategoryForm {

    @NotBlank
    @Length(max = 255)
    private String name;

    @NotBlank
    @Length(max = 255)
    @Pattern(regexp = "^[a-z0-9-]+$")
    private String slug;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Category convert() {
        return new Category(name, slug);
    }
}
