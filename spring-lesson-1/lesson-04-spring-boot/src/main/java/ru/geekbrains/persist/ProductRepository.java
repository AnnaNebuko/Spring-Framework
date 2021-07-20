package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository <Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p " +
            "from Product p " +
            "where ( p.name like CONCAT(:prefix, '%') or :prefix is null ) and " +
            "( p.price >= :minPrice or :minPrice is null ) and " +
            "( p.price <= :maxPrice or :maxPrice is null )")
    List<Product> filterUsers(@Param("prefix") String prefix,
                           @Param("minPrice") Integer minAge,
                           @Param("maxPrice") Integer maxAge);

}