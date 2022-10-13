package br.com.example.deliveryservice.domain.adapter;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderProductDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class PayloadAdapter {

    private PayloadAdapter() {

    }

    public static Order fromOrderPayloadToOrder(OrderPayload orderPayload, List<OrderProductDTO> products, OrderStatus status,
                                                Long totalInCents, LocalDateTime deliveryDate, Map<String, String> extra) {

        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderKey(orderPayload.getOrderKey())
                .status(status)
                .note(orderPayload.getNote())
                .paymentMethod(orderPayload.getPaymentMethod())
                .username(orderPayload.getUsername())
                .address(orderPayload.getAddress())
                .totalInCents(totalInCents)
                .orderPriority(orderPayload.getOrderPriority())
                .deliveryTime(deliveryDate)
                .extra(extra)
                .products(new ArrayList<>(products.size()))
                .build();

        products.forEach(op -> order.addProduct(op.getProduct(), op.getQuantity()));

        return order;
    }


}
