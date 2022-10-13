package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.OrderStatus;
import br.com.example.deliveryservice.infra.security.validator.sanitized.SanitizedString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPatchPayload {

    @NotNull
    private OrderStatus status;

    @SanitizedString
    private String note;

    @SanitizedString
    private String address;
}
