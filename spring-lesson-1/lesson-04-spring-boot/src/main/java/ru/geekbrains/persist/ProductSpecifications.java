package ru.geekbrains.persist;

import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecifications {
    public static Specification<Product> usernamePrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }

    public static Specification<Product> minPrice(Integer minPrice) {
        return (root, query, builder) -> builder.ge(root.get("price"), minPrice);
        //greater or equals
    }

    public static Specification<Product> maxPrice(Integer maxPrice) {
        return (root, query, builder) -> builder.le(root.get("price"), maxPrice);
        //less or equals
    }
}
