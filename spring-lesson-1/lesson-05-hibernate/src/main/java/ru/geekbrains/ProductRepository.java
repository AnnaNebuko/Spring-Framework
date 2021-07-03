package ru.geekbrains;

import ru.geekbrains.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductRepository {
    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Product> findAll() {
        return executeForEntityManager(
                em -> em.createNamedQuery("allProducts", Product.class).getResultList()
        );
    }

    public Product findById(int id) {
        return executeForEntityManager(
                em -> em.find(Product.class, id)
        );
    }

    public void deleteByID(int id) {
        executeInTransaction(
                em -> em.createQuery("delete from Product where id = :id")
                .setParameter("id", id)
                .executeUpdate()
        );
    }

    public void insert (Product product) {
        executeInTransaction(em -> em.persist(product));
    }

    public void save(Product product){
        if(product.getId() == null){
            insert(product);
        }
        else update(product);
    }
    public void update(Product product) {
        executeInTransaction(em -> em.merge(product));
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

}
