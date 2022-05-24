package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.ProductType;
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
    private String productKey;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private ProductType productType;

    @NotNull
    private BigDecimal productValue;

    private Map<String, String> extra;

}
