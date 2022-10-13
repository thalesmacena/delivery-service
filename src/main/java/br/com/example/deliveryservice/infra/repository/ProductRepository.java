package br.com.example.deliveryservice.infra.repository;

import br.com.example.deliveryservice.domain.internal.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProductKey(String key);

    Page<Product> findByNameContainingIgnoreCase(String key, Pageable pageable);

    Page<Product> findByProductKeyContainingIgnoreCase(String key, Pageable pageable);

    List<Product> findByProductKeyIn(List<String> productKeys);

    void deleteByProductKey(String productKey);

    boolean existsByProductKey(String productKey);
}
