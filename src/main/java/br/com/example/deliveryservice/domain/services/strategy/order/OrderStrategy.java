package br.com.example.deliveryservice.domain.services.strategy.order;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.ProductType;
import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.internal.dto.OrderProductDTO;

import java.util.List;

public interface OrderStrategy {

    Order handleOrder(OrderPayload orderPayload, List<OrderProductDTO> orderProductList);

    ProductType getOrderPriorityStrategy();
}
