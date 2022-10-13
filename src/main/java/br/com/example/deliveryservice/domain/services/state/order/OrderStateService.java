package br.com.example.deliveryservice.domain.services.state.order;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.services.state.order.impl.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderStateService {

     private OrderState orderState;
     private Order order;

     public Order getOrder() {
         return this.order;
     }

    public void appendNote(String note) {
         orderState.appendNote(this.order, note);
    }

    public void setAddress(String address) {
         orderState.setAddress(this.order, address);
    }

     public void handleTargetStatus(OrderStatus target) {
         switch (target) {
             case IN_TRANSIT -> transitOrder();
             case DISPATCHED -> dispatchOrder();
             case FINISHED -> finishOrder();
             case CANCELLED -> cancelOrder();
             case IN_PREPARATION -> prepareOrder();
             case PENDING -> pendingOrder();
         }
     }

    private void transitOrder() {
        this.order = orderState.toInTransitStatus(order);
        this.orderState = new InTransitState();
    }

    private void dispatchOrder() {
        this.order = orderState.toDispatchedStatus(order);
        this.orderState = new DispatchedState();
    }

    private void finishOrder() {
        this.order = orderState.toFinishedStatus(order);
        this.orderState = new FinishedState();
    }

     private void cancelOrder() {
         this.order = orderState.toCancelledStatus(order);
         this.orderState = new CancelledState();
     }

    private void prepareOrder() {
        this.order = orderState.toInPreparationStatus(order);
        this.orderState = new InPreparationState();
    }

    private void pendingOrder() {
        this.order = orderState.toPendingStatus(order);
        this.orderState = new PendingState();
    }

}
