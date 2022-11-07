package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.OrderProduct;
import br.com.example.deliveryservice.domain.internal.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventProductDTO {

    private String productKey;
    private String name;
    private ProductType productType;
    private BigDecimal productValue;
    private Long quantity;

    @Builder.Default
    private Map<String, String> extra = new HashMap<>();

    public static EventProductDTO ofOrderProduct(OrderProduct orderProduct) {
        return EventProductDTO.builder()
                .productKey(orderProduct.getProduct().getProductKey())
                .name(orderProduct.getProduct().getName())
                .productType(orderProduct.getProduct().getProductType())
                .productValue(orderProduct.getProduct().getProductValue())
                .extra(orderProduct.getProduct().getExtra())
                .quantity(orderProduct.getQuantity())
                .build();
    }

}
