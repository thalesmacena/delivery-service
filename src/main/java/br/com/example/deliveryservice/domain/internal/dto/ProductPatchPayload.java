package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPatchPayload {

    private String productKey;
    private String name;
    private String description;
    private ProductType productType;
    private BigDecimal productValue;
    private Map<String, String> extra;
}
