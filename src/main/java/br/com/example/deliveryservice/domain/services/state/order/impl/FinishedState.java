package br.com.example.deliveryservice.domain.services.state.order.impl;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.services.state.order.OrderState;
import org.springframework.stereotype.Service;

@Service
public class FinishedState implements OrderState {

    @Override
    public OrderStatus getState() {
        return OrderStatus.FINISHED;
    }

    @Override
    public void appendNote(Order order, String note) {
        order.appendNote(note);
    }

}
