package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.infra.security.validator.key.ValidKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderProductPayload implements Serializable {

    @ValidKey
    private String productKey;

    @Min(value = 1)
    private Long quantity;

}
