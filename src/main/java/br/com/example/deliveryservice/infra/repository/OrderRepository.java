package br.com.example.deliveryservice.infra.repository;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Page<Order> findByUsernameAndStatus(String username, OrderStatus orderStatus, Pageable pageable);

    Page<Order> findByUsername(String username, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Optional<Order> findByOrderKey(String orderKey);
}