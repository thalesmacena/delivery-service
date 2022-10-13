package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.internal.dto.OrderPatchPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<Order> findAll(Pageable pageable);

    Page<Order> findByUsernameAndStatus(String username, OrderStatus status, Pageable pageable);

    Page<Order> findByUsername(String username, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Order findById(String orderId);

    Order findByOrderKey(String orderKey);

    OrderPayload sendToProcess(OrderPayload payload);

    void processPayload(OrderPayload payload);

    Order patchStatusByOrderKey(String orderKey, OrderPatchPayload patchFields);

}
