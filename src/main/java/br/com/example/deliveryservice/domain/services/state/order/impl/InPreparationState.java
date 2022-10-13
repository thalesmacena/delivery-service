package br.com.example.deliveryservice.domain.services.state.order.impl;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.services.state.order.OrderState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InPreparationState implements OrderState {

    @Override
    public OrderStatus getState() {
        return OrderStatus.IN_PREPARATION;
    }

    @Override
    public void appendNote(Order order, String note) {
        order.appendNote(note);
    }

    @Override
    public void setAddress(Order order, String address) {
        order.setAddress(address);
    }

    @Override
    public Order toDispatchedStatus(Order order) {
        order.setStatus(OrderStatus.DISPATCHED);
        return order;
    }

    @Override
    public Order toCancelledStatus(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        order.setEndDate(LocalDateTime.now());
        return order;
    }

}
