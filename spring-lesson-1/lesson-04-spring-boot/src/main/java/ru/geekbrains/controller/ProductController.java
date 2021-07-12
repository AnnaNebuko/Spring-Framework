package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecifications;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(Model model,
                           @RequestParam("usernameFilter") Optional<String> usernameFilter,
                           @RequestParam("minPrice") Optional<Integer> minPrice,
                           @RequestParam("maxPrice") Optional<Integer> maxPrice,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        logger.info("User list page requested");

        Specification<Product> spec = Specification.where(null);
        if (usernameFilter.isPresent() && !usernameFilter.get().isBlank()) {
            spec = spec.and(ProductSpecifications.usernamePrefix(usernameFilter.get()));
        }
        if (minPrice.isPresent()) {
            spec = spec.and(ProductSpecifications.minPrice(minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            spec = spec.and(ProductSpecifications.maxPrice(maxPrice.get()));
        }

        model.addAttribute("products", productRepository.findAll(spec,
                PageRequest.of(page.orElse(1) - 1, size.orElse(3))));

        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");

        model.addAttribute("product", new Product());
        return "product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Edit page for id {} requested", id);

        model.addAttribute("product", productRepository.findById(id));
        return "product_form";
    }

    @PostMapping
    public String update(Product product, BindingResult result) {
        logger.info("Saving product");

        if (result.hasErrors()) {
            return "product_form";
        }

        productRepository.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        logger.info("Deleting product with id {}", id);

        productRepository.deleteById(id);
        return "redirect:/product";
    }
}