package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Main05 {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductRepository productRepo = new ProductRepository(emFactory);
        Product water = new Product(null, "Water", 35);

        productRepo.save(water);
        Product product = productRepo.findById(1);
        System.out.println(product);

        List<Product> products = productRepo.findAll();
        productRepo.deleteByID(1);

        Product productNewWater = new Product(4, "New water", 40);
        productRepo.update(productNewWater);
    }
}
