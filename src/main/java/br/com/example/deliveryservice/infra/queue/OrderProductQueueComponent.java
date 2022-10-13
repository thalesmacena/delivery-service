package br.com.example.deliveryservice.infra.queue;

import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;

public interface OrderProductQueueComponent {

    void sendOrderToOrderProcessQueue(OrderPayload order);
}
