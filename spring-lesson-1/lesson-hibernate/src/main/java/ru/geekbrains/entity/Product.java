package ru.geekbrains.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name ="allProducts", query = "select p from Product p")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    private Customer customer;

    public Product() {
    }

    public Product(Integer id, String name, Integer price, Customer customer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", customer=" + customer.getName() +
                '}';
    }
}

