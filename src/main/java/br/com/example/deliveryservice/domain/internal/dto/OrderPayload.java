package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.PaymentMethod;
import br.com.example.deliveryservice.domain.internal.ProductType;
import br.com.example.deliveryservice.infra.security.validator.key.ValidKey;
import br.com.example.deliveryservice.infra.security.validator.sanitized.SanitizedString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPayload implements Serializable {

    @NotEmpty
    @ValidKey
    private String orderKey;

    @SanitizedString
    private String note;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private ProductType orderPriority;

    @SanitizedString
    @NotEmpty
    private String username;

    @NotEmpty
    private List<OrderProductPayload> products = new ArrayList<>();

    @NotEmpty
    @SanitizedString
    private String address;

    private Map<String, String> extra;
}
