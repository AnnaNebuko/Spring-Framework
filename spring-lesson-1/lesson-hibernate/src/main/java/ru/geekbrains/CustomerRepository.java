package ru.geekbrains;

import ru.geekbrains.entity.Customer;
import ru.geekbrains.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomerRepository {
    private final EntityManagerFactory emFactory;

    public CustomerRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Consumer> findAll() {
        return executeForEntityManager(
                em -> em.createNamedQuery("allCustomers", Consumer.class).getResultList()
        );
    }

    public Product findById(int id) {
        return executeForEntityManager(
                em -> em.find(Product.class, id)
        );
    }

    public void deleteByID(int id) {
        executeInTransaction(
                em -> em.createQuery("delete from Customer where id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    public void insert (Customer customer) {
        executeInTransaction(em -> em.persist(customer));
    }

    public void save(Customer customer){
        if(customer.getId() == null){
            insert(customer);
        }
        else update(customer);
    }
    public void update(Customer customer) {
        executeInTransaction(em -> em.merge(customer));
    }

    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void findProducts(int i) {
        EntityManager em = emFactory.createEntityManager();
        Customer customer = em.createQuery("select c from Customer c join fetch c.products where c.id = :id", Customer.class)
                .setParameter("id", i)
                .getSingleResult();
        System.out.println(customer);
        customer.getProducts().forEach(System.out::println);
    }
}
