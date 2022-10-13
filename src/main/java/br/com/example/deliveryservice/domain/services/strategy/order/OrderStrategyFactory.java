package br.com.example.deliveryservice.domain.services.strategy.order;

import br.com.example.deliveryservice.domain.internal.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Component
public class OrderStrategyFactory {

    private Map<ProductType, OrderStrategy> orderStrategyMap;

    @Autowired
    public OrderStrategyFactory (Set<OrderStrategy> orderStrategySet){
        createStrategy(orderStrategySet);
    }

    private void createStrategy(Set<OrderStrategy> orderStrategySet) {
        orderStrategyMap = new EnumMap<>(ProductType.class);
        orderStrategySet.forEach(strategy -> orderStrategyMap.put(strategy.getOrderPriorityStrategy(), strategy));
    }

    public OrderStrategy findOrderPriorityStrategy(ProductType orderPriority){
        OrderStrategy orderPriorityStrategy = orderStrategyMap.get(orderPriority);

        if (isNull(orderPriorityStrategy)){
            throw new UnsupportedOperationException("Order Priority Strategy not implemented");
        }

        return orderPriorityStrategy;
    }

}
