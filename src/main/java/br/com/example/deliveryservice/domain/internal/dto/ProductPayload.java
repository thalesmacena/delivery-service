package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.ProductType;
import br.com.example.deliveryservice.infra.security.validator.key.ValidKey;
import br.com.example.deliveryservice.infra.security.validator.sanitized.SanitizedString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPayload {

    @NotEmpty
    @ValidKey
    private String productKey;

    @NotEmpty
    @SanitizedString
    private String name;

    @SanitizedString
    private String description;

    @NotNull
    private ProductType productType;

    @NotNull
    private BigDecimal productValue;

    private Map<String, String> extra;

}
