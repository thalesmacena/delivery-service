package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.services.ProductService;
import br.com.example.deliveryservice.infra.exception.internal.ProductKeyAlreadyInUse;
import br.com.example.deliveryservice.infra.exception.internal.ProductNotFoundException;
import br.com.example.deliveryservice.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByNameStartsWith(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Product> findByProductKeyStartsWith(String productKey, Pageable pageable) {
        return productRepository.findByProductKeyContainingIgnoreCase(productKey, pageable);
    }

    @Override
    public Product findById(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Override
    public Product findByProductKey(String productKey) {
        Optional<Product> optionalProduct = productRepository.findByProductKey(productKey);
        return optionalProduct.orElseThrow(() -> new ProductNotFoundException(productKey));
    }

    @Override
    public Product save(Product product) {
        Optional<Product> optionalProduct = productRepository.findByProductKey(product.getProductKey());

        if (optionalProduct.isPresent()) {
            throw new ProductKeyAlreadyInUse(product.getProductKey());
        }

        return productRepository.save(product);
    }

    @Override
    public void delete(String productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteByProductKey(String productKey) {
        productRepository.deleteByProductKey(productKey);
    }
}
