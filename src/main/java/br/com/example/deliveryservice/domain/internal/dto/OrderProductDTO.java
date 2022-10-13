package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.Product;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {

    private Product product;

    private Long quantity;

}
