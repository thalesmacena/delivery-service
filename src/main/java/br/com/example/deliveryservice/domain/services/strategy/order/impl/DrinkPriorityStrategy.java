package br.com.example.deliveryservice.domain.services.strategy.order.impl;

import static br.com.example.deliveryservice.domain.adapter.PayloadAdapter.fromOrderPayloadToOrder;
import static br.com.example.deliveryservice.infra.util.OrderUtils.getOrderProductTotalValueInCents;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.internal.ProductType;
import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderProductDTO;
import br.com.example.deliveryservice.domain.services.strategy.order.OrderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkPriorityStrategy implements OrderStrategy {

    @Override
    public Order handleOrder(OrderPayload orderPayload, List<OrderProductDTO> orderProductList) {
        Long totalInCents = calculatePriorityTotalValueInCents(orderProductList);

        LocalDateTime calculateDeliveryTime = calculateDeliveryTime();

        orderPayload.getExtra().put("FREEZE", "true");

        return fromOrderPayloadToOrder(orderPayload, orderProductList, OrderStatus.IN_PREPARATION, totalInCents, calculateDeliveryTime, orderPayload.getExtra());
    }

    private Long calculatePriorityTotalValueInCents(List<OrderProductDTO> orderProductList) {
        Long partialTotal = getOrderProductTotalValueInCents(orderProductList);

        return Long.sum(partialTotal, 500L);
    }

    private LocalDateTime calculateDeliveryTime() {
        return LocalDateTime.now().plusMinutes(30L);
    }

    @Override
    public ProductType getOrderPriorityStrategy() {
        return ProductType.DRINK;
    }
}
