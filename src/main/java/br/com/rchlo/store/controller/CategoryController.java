package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Category;
import br.com.rchlo.store.dto.CategoryDto;
import br.com.rchlo.store.form.CategoryForm;
import br.com.rchlo.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<CategoryDto> categoryList() {
        List<Category> categories = categoryRepository.findAllByOrderByPosition();

        return CategoryDto.convert(categories);
    }

    @GetMapping("/admin/categories/{id}")
    public ResponseEntity<CategoryDto> detail(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            return ResponseEntity.ok(new CategoryDto(category.get()));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/admin/categories")
    @Transactional
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryForm form, UriComponentsBuilder uriBuilder) {
        Category category = form.convert();
        int lastPosition = categoryRepository.findLastPosition();
        category.updatePosition(lastPosition);
        categoryRepository.save(category);

        URI uri = uriBuilder.path("/admin/categories/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(uri).body(new CategoryDto(category));
    }

    @DeleteMapping("admin/categories/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
