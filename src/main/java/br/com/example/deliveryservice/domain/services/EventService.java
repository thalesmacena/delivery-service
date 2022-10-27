package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.internal.Order;

public interface EventService {

    void saveOrderEventToSend(Order order);

    void flushBatchEvents();

}
