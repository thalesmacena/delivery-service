package br.com.example.deliveryservice.domain.services.impl;

import static java.util.Objects.nonNull;

import br.com.example.deliveryservice.domain.internal.*;
import br.com.example.deliveryservice.domain.internal.dto.OrderPatchPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderProductDTO;
import br.com.example.deliveryservice.domain.internal.dto.OrderProductPayload;
import br.com.example.deliveryservice.domain.services.CacheLockService;
import br.com.example.deliveryservice.domain.services.OrderService;
import br.com.example.deliveryservice.domain.services.ProductService;
import br.com.example.deliveryservice.domain.services.state.order.OrderStateFactory;
import br.com.example.deliveryservice.domain.services.state.order.OrderStateService;
import br.com.example.deliveryservice.domain.services.strategy.order.OrderStrategy;
import br.com.example.deliveryservice.domain.services.strategy.order.OrderStrategyFactory;
import br.com.example.deliveryservice.infra.exception.internal.OrderInProcessException;
import br.com.example.deliveryservice.infra.exception.internal.OrderKeyAlreadyInUseException;
import br.com.example.deliveryservice.infra.exception.internal.OrderNotFoundException;
import br.com.example.deliveryservice.infra.exception.internal.ProductNotFoundException;
import br.com.example.deliveryservice.infra.queue.OrderProductQueueComponent;
import br.com.example.deliveryservice.infra.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductService productService;

    private final OrderProductQueueComponent orderProductQueueComponent;

    private final OrderStrategyFactory orderStrategyFactory;

    private final OrderStateFactory orderStateFactory;

    private final CacheLockService cacheLockService;

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findByUsernameAndStatus(String username, OrderStatus status, Pageable pageable) {
        return orderRepository.findByUsernameAndStatus(username, status, pageable);
    }


    @Override
    public Page<Order> findByUsername(String username, Pageable pageable) {
        return orderRepository.findByUsername(username, pageable);
    }

    @Override
    public Page<Order> findByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }

    @Override
    public Order findById(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public Order findByOrderKey(String orderKey) {
        Optional<Order> optionalOrder = orderRepository.findByOrderKey(orderKey);
        return optionalOrder.orElseThrow(() -> new OrderNotFoundException(orderKey));
    }

    @Override
    public OrderPayload sendToProcess(OrderPayload payload) {
        Optional<Order> existentOrder = orderRepository.findByOrderKey(payload.getOrderKey());

        if (existentOrder.isPresent()) {
            throw new OrderKeyAlreadyInUseException(payload.getOrderKey());
        }

        if (this.cacheLockService.isLocked(payload.getOrderKey())) {
            throw new OrderInProcessException(payload.getOrderKey());
        }

        List<String> productsKeyList = payload.getProducts().stream().map(OrderProductPayload::getProductKey).toList();

        if (!this.productService.checkIfAllListedProductsAreValid(productsKeyList, payload.getOrderPriority())) {
            throw new ProductNotFoundException(String.join(", ", productsKeyList));
        }

        try {
            cacheLockService.lock(payload.getOrderKey(), payload);
            orderProductQueueComponent.sendOrderToOrderProcessQueue(payload);
        } catch (Exception e) {
            cacheLockService.unlock(payload.getOrderKey());
            throw e;
        }

        return payload;
    }

    @Override
    public void processPayload(OrderPayload payload) {
        List<OrderProductDTO> orderProductList = getOrderProductList(payload.getProducts());

        OrderStrategy strategy = orderStrategyFactory.findOrderPriorityStrategy(payload.getOrderPriority());
        Order order = strategy.handleOrder(payload, orderProductList);

        orderRepository.save(order);

        cacheLockService.unlock(payload.getOrderKey());
    }

    private List<OrderProductDTO> getOrderProductList(List<OrderProductPayload> products) {
        return products.stream().map(product -> {
            Product enrichedProduct = productService.findByProductKey(product.getProductKey());

            return new OrderProductDTO(enrichedProduct, product.getQuantity());
        }).toList();
    }

    @Override
    public Order patchStatusByOrderKey(String orderKey, OrderPatchPayload patchFields) {
        Order orderSaved = orderRepository.findByOrderKey(orderKey).orElseThrow(() -> new OrderNotFoundException(orderKey));

        OrderStateService stateService = orderStateFactory.getOrderStateService(orderSaved);

        stateService.handleTargetStatus(patchFields.getStatus());

        if (nonNull(patchFields.getNote())) {
            stateService.appendNote(patchFields.getNote());
        }

        if (nonNull(patchFields.getAddress())) {
            stateService.setAddress(patchFields.getAddress());
        }

        return orderRepository.save(stateService.getOrder());
    }
}
