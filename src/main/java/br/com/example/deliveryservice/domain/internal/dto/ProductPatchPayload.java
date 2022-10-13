package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.ProductType;
import br.com.example.deliveryservice.infra.security.validator.key.ValidKey;
import br.com.example.deliveryservice.infra.security.validator.sanitized.SanitizedString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPatchPayload {

    @ValidKey
    private String productKey;
    @SanitizedString
    private String name;
    @SanitizedString
    private String description;
    private ProductType productType;
    private BigDecimal productValue;
    private Map<String, String> extra;
}
