package br.com.example.deliveryservice.infra.util;

import br.com.example.deliveryservice.domain.internal.dto.OrderProductDTO;

import java.util.List;

public class OrderUtils {

    public static Long getOrderProductTotalValueInCents(List<OrderProductDTO> orderProductList) {
        return orderProductList.stream().reduce(0L, OrderUtils::calculatePartialTotalInCents, Long::sum);
    }

    private static Long calculatePartialTotalInCents(Long partial, OrderProductDTO orderProduct) {
        long partialSum = orderProduct.getProduct().getProductValue().longValueExact() * orderProduct.getQuantity();

        return Long.sum(partial, partialSum);
    }
}
