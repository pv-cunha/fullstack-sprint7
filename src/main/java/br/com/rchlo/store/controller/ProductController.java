package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductByColorDto;
import br.com.rchlo.store.dto.ProductDto;
import br.com.rchlo.store.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    @Cacheable(value = "productsList")
    public List<ProductDto> products() {

        return productRepository.findAllByOrderByName().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    @GetMapping("/reports/products/by-color")
    public List<ProductByColorDto> productByColorReport() {
        return productRepository.productsByColor();
    }

    @GetMapping("/products/clear-cache")
    @CacheEvict(value = "productsList", allEntries = true)
    public String cleanCache() {
        return "The product list cache has been cleared !";
    }

    @GetMapping("/products/pagination")
    public Page<ProductDto> productsPagination(
            @RequestParam(required = false) String name,
            @PageableDefault(sort="name", direction = Sort.Direction.ASC) Pageable pagination) {

        if (name == null) {

            Page<Product> products = productRepository.findAll(pagination);

            return ProductDto.convert(products);

        } else {

            Page<Product> products = productRepository.findByName(name, pagination);

            return ProductDto.convert(products);

        }
    }

}
