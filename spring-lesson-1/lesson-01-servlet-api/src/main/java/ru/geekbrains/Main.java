package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Cart cart = context.getBean("cart", Cart.class);
        System.out.println("Type id for adding (1-5)");
        Scanner scanner = new Scanner(System.in);
        Long id = scanner.nextLong();
        cart.add(id);

        System.out.println("Type id for removing (1-5)");
        id = scanner.nextLong();
        cart.delete(id);

        System.out.println("OK");
    }
}
