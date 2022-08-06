package br.com.example.deliveryservice.domain.services.impl;

import static br.com.example.deliveryservice.infra.util.ClassUtils.getNullPropertyNames;

import br.com.example.deliveryservice.domain.adpter.ProductMapper;
import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.internal.dto.ProductPatchPayload;
import br.com.example.deliveryservice.domain.internal.dto.ProductPayload;
import br.com.example.deliveryservice.domain.services.ProductImageService;
import br.com.example.deliveryservice.domain.services.ProductService;
import br.com.example.deliveryservice.infra.exception.internal.ProductKeyAlreadyInUse;
import br.com.example.deliveryservice.infra.exception.internal.ProductNotFoundException;
import br.com.example.deliveryservice.infra.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productAdapter;

    private final ProductImageService productImageService;

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
    public Product save(ProductPayload payload) {
        Optional<Product> optionalProduct = productRepository.findByProductKey(payload.getProductKey());

        if (optionalProduct.isPresent()) {
            throw new ProductKeyAlreadyInUse(payload.getProductKey());
        }

        return productRepository.save(productAdapter.toEntity(payload));
    }

    @Override
    public Product updateById(String productId, ProductPayload payload) {
        Product productSaved = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        Product updatedProduct = productAdapter.toEntity(payload);

        updatedProduct.setId(productSaved.getId());

        return productRepository.save(updatedProduct);
    }

    @Override
    public Product updateByProductKey(String productKey, ProductPayload payload) {
        Product productSaved = productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        Product updatedProduct = productAdapter.toEntity(payload);

        updatedProduct.setId(productSaved.getId());

        return productRepository.save(updatedProduct);
    }

    @Override
    public Product patchById(String productId, ProductPatchPayload patchFields) {
        Product productSaved = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        BeanUtils.copyProperties(patchFields, productSaved, getNullPropertyNames(patchFields));

        return productRepository.save(productSaved);
    }

    @Override
    public Product patchByProductKey(String productKey, ProductPatchPayload patchFields) {
        Product productSaved = productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        BeanUtils.copyProperties(patchFields, productSaved, getNullPropertyNames(patchFields));

        return productRepository.save(productSaved);
    }

    @Override
    public void delete(String productId) {
        Product productSaved = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        productImageService.deleteImagesByProductKeyAndImages(productSaved.getProductKey(), productSaved.getImages());

        productRepository.delete(productSaved);
    }

    @Override
    public void deleteByProductKey(String productKey) {
        Product productSaved = productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        productImageService.deleteImagesByProductKeyAndImages(productSaved.getProductKey(), productSaved.getImages());

        productRepository.delete(productSaved);
    }
}
