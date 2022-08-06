package br.com.example.deliveryservice.infra.repository;

import br.com.example.deliveryservice.domain.internal.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ImageRepository extends JpaRepository<Image, String> {

    Set<Image> findByProductProductKey(String productKey);

    Optional<Image> findByProductProductKeyAndKey(String productKey, String key);

    Set<Image> findByProductProductKeyAndKeyIn(String productKey, List<String> keys);
}
