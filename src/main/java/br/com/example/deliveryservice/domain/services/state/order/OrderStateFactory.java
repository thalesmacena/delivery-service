package br.com.example.deliveryservice.domain.services.state.order;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderStateFactory {

    private Map<OrderStatus, OrderState> orderStateMap;

    @Autowired
    public OrderStateFactory (Set<OrderState> orderStateSet){
        createStates(orderStateSet);
    }

    private void createStates(Set<OrderState> orderStateSet) {
        orderStateMap = new EnumMap<>(OrderStatus.class);
        orderStateSet.forEach(state -> orderStateMap.put(state.getState(), state));
    }

    public OrderStateService getOrderStateService(Order order){
        OrderState orderState = orderStateMap.get(order.getStatus());

        return new OrderStateService(orderState, order);
    }

}
