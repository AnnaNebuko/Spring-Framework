package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Customer;
import ru.geekbrains.entity.Product;

import javax.persistence.EntityManagerFactory;

public class Main06 {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductRepository productRepo = new ProductRepository(emFactory);
        Customer anna = new Customer(null, "Anna");

        Product yogurt = new Product(null, "Yogurt", 50, anna);
        Product cottageCheese = new Product(null, "Cottage Cheese", 60, anna);
        Product coffee = new Product(null, "Coffee", 20, anna);

        productRepo.save(yogurt);
        productRepo.save(cottageCheese);
        productRepo.save(coffee);

        CustomerRepository customerRepo = new CustomerRepository(emFactory);
        customerRepo.save(anna);

        //Перечень продуктов, который купил покупатель с определенным id
        customerRepo.findProducts(1);

        //Один из покупателей, который купил продукт с определенным id продукта (только так получилось)
        productRepo.findCustomers(1);
    }
}
