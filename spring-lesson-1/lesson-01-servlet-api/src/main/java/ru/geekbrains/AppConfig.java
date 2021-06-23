package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductRepositoryImpl;

@Configuration
@ComponentScan({"ru.geekbrains"})
public class AppConfig {
    public AppConfig() {
    }

    @Bean
    public ProductRepositoryImpl productRepository() {
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        productRepository.save(new Product(1L, "Product 1", 3L));
        productRepository.save(new Product(2L, "Product 2", 4L));
        productRepository.save(new Product(3L, "Product 3", 5L));
        productRepository.save(new Product(4L, "Product 4", 4L));
        productRepository.save(new Product(5L, "Product 5", 5L));
        return productRepository;
    }

    @Bean
    @Scope("prototype")
    public Cart cart(ProductRepository productRepository ){
        return new Cart(productRepository);
    }
}
