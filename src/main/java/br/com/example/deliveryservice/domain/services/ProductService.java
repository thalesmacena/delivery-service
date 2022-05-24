package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.internal.dto.ProductPatchPayload;
import br.com.example.deliveryservice.domain.internal.dto.ProductPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameStartsWith(String name, Pageable pageable);

    Page<Product> findByProductKeyStartsWith(String productKey, Pageable pageable);

    Product findById(String productId);

    Product findByProductKey(String productKey);

    Product save(ProductPayload payload);

    Product updateById(String productId, ProductPayload payload);

    Product updateByProductKey(String productKey, ProductPayload payload);

    Product patchById(String productId, ProductPatchPayload patchFields);

    Product patchByProductKey(String productKey, ProductPatchPayload patchFields);

    void delete(String productId);

    void deleteByProductKey(String productKey);
}
