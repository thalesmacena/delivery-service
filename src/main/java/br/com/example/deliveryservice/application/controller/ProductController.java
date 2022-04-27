package br.com.example.deliveryservice.application.controller;

import static org.springframework.http.ResponseEntity.status;
import static java.util.Objects.nonNull;

import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<Product> findAll(@RequestParam(value = "productKey", required = false) String productKey,
                                 @RequestParam(value = "productName", required = false) String productName,
                                 Pageable pageable) {
        if (nonNull(productKey)) {
            return this.productService.findByProductKeyStartsWith(productKey, pageable);
        }

        if (nonNull(productName)) {
            return this.productService.findByNameStartsWith(productName, pageable);
        }

        return this.productService.findAll(pageable);
    }

    @GetMapping("id/{id}")
    public Product findById(@PathVariable String id) {
        return this.productService.findById(id);
    }

    @GetMapping("key/{productKey}")
    public Product findByProductKey(@PathVariable String productKey) {
        return productService.findByProductKey(productKey);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return status(HttpStatus.CREATED).body(productService.save(product));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.productService.delete(id);
        return status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("key/{productKey}")
    public ResponseEntity<Void> deleteByProductKey(@PathVariable String productKey) {
        this.productService.deleteByProductKey(productKey);
        return status(HttpStatus.NO_CONTENT).build();
    }

}
