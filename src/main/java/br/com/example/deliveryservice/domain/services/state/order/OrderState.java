package br.com.example.deliveryservice.domain.services.state.order;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.infra.exception.internal.InvalidOrderStatusToUpdateFieldException;
import br.com.example.deliveryservice.infra.exception.internal.InvalidTargetOrderStatusException;

public interface OrderState {

    OrderStatus getState();

    default void appendNote(Order order, String note) {
       throw new InvalidOrderStatusToUpdateFieldException("note", order);
    }

    default void setAddress(Order order, String address) {
        throw new InvalidOrderStatusToUpdateFieldException("address", order);
    }

    default Order toPendingStatus(Order order) {
        throw new InvalidTargetOrderStatusException(order, OrderStatus.PENDING);
    }

    default Order toInPreparationStatus(Order order) {
        throw new InvalidTargetOrderStatusException(order, OrderStatus.IN_PREPARATION);
    }

    default Order toDispatchedStatus(Order order) {
        throw new InvalidTargetOrderStatusException(order, OrderStatus.DISPATCHED);
    }

    default Order toInTransitStatus(Order order) {
        throw new InvalidTargetOrderStatusException(order, OrderStatus.IN_TRANSIT);
    }

    default Order toFinishedStatus(Order order) {
        throw new InvalidTargetOrderStatusException(order, OrderStatus.FINISHED);
    }

    default Order toCancelledStatus(Order order) {
        throw new InvalidTargetOrderStatusException(order, OrderStatus.CANCELLED);
    }
}
