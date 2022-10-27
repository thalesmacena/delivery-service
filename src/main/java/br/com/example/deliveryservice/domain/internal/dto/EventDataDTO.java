package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.domain.internal.PaymentMethod;
import br.com.example.deliveryservice.domain.internal.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDataDTO {

    private String orderKey;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private String username;
    private String address;
    private Long totalInCents;
    private ProductType orderPriority;
    private LocalDateTime deliveryTime;
    private LocalDateTime endDate;
    private LocalDateTime entryDate;
    private LocalDateTime updatedDate;

    @Builder.Default
    private Set<EventProductDTO> productSet = new HashSet<>();

    @Builder.Default
    private Map<String, String> extra = new HashMap<>();

    public static EventDataDTO ofOrder(Order order) {
        return EventDataDTO.builder()
                .orderKey(order.getOrderKey())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .username(order.getUsername())
                .address(order.getAddress())
                .totalInCents(order.getTotalInCents())
                .orderPriority(order.getOrderPriority())
                .deliveryTime(order.getDeliveryTime())
                .endDate(order.getEndDate())
                .entryDate(order.getEntryDate())
                .updatedDate(order.getUpdatedDate())
                .extra(order.getExtra())
                .productSet(order.getProducts().stream()
                        .map(EventProductDTO::ofOrderProduct).collect(Collectors.toSet()))
                .build();
    }


}
