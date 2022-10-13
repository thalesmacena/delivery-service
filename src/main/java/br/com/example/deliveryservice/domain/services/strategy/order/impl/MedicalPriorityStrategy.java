package br.com.example.deliveryservice.domain.services.strategy.order.impl;

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

import static br.com.example.deliveryservice.domain.adapter.PayloadAdapter.fromOrderPayloadToOrder;
import static br.com.example.deliveryservice.infra.util.OrderUtils.getOrderProductTotalValueInCents;

@Service
@RequiredArgsConstructor
public class MedicalPriorityStrategy implements OrderStrategy {
    @Override
    public Order handleOrder(OrderPayload orderPayload, List<OrderProductDTO> orderProductList) {
        Long totalInCents = calculatePriorityTotalValueInCents(orderProductList);

        LocalDateTime calculateDeliveryTime = calculateDeliveryTime();

        return fromOrderPayloadToOrder(orderPayload, orderProductList, OrderStatus.IN_PREPARATION, totalInCents, calculateDeliveryTime, orderPayload.getExtra());
    }

    private Long calculatePriorityTotalValueInCents(List<OrderProductDTO> orderProductList) {
        Long partialTotal = getOrderProductTotalValueInCents(orderProductList);

        return Long.sum(partialTotal, 1000L);
    }

    private LocalDateTime calculateDeliveryTime() {
        return LocalDateTime.now();
    }

    @Override
    public ProductType getOrderPriorityStrategy() {
        return ProductType.MEDICAL;
    }
}
