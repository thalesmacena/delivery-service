package br.com.example.deliveryservice.application.controller;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.internal.dto.OrderPatchPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.nonNull;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public Page<Order> findByUsernameAndOrStatus(@RequestParam(required = false) String username,
                                                      @RequestParam(required = false) OrderStatus status,
                                                      Pageable pageable) {

        if (nonNull(username) && nonNull(status)) {
            return orderService.findByUsernameAndStatus(username, status, pageable);
        }

        if (nonNull(username)) {
            return orderService.findByUsername(username, pageable);
        }

        if (nonNull(status)) {
            return orderService.findByStatus(status, pageable);
        }

        return this.orderService.findAll(pageable);
    }

    @GetMapping("id/{id}")
    public Order findById(@PathVariable String id) {
        return this.orderService.findById(id);
    }

    @GetMapping("key/{orderKey}")
    public Order findByOrderKey(@PathVariable String orderKey) {
        return this.orderService.findByOrderKey(orderKey);
    }

    @PostMapping
    public ResponseEntity<OrderPayload> sendToProcess(@RequestBody @Valid OrderPayload payload) {
        return status(HttpStatus.ACCEPTED).body(orderService.sendToProcess(payload));
    }

    @PatchMapping("key/{orderKey}")
    public ResponseEntity<Order> patchStatusByOrderKey(@PathVariable String orderKey, @RequestBody @Valid OrderPatchPayload patchFields) {
        return ResponseEntity.ok(this.orderService.patchStatusByOrderKey(orderKey, patchFields));
    }
}
