package br.com.rchlo.store.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    private Long code;

    private String name;

    private String description;

    private String slug;

    private String brand;

    private BigDecimal price;

    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private Color color;

    private Integer weightInGrams;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Size> availableSizes;

    public Product() {
    }

    public Product(Long code, String name, String description, String slug, String brand, BigDecimal price, BigDecimal discount, Color color, Integer weightInGrams) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.brand = brand;
        this.price = price;
        this.discount = discount;
        this.color = color;
        this.weightInGrams = weightInGrams;
    }

    public Long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return slug;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Color getColor() {
        return color;
    }

    public Integer getWeightInGrams() {
        return weightInGrams;
    }

    public Category getCategory() {
        return category;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public List<Size> getAvailableSizes() {
        return availableSizes;
    }
}
