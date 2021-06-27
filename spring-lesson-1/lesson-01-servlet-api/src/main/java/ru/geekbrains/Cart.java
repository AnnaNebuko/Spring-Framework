package ru.geekbrains;

import org.springframework.stereotype.Component;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Cart {
    private final Map <Long, Product> cartMap = new ConcurrentHashMap<>();
    ProductRepository productRepository = new ProductRepositoryImpl();

    public Cart(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void delete(long id) {
        cartMap.remove(id);
    }

    public void add(long id) {
        cartMap.put(id, productRepository.findById(id));
    }
}
