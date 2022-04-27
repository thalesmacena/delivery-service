package br.com.example.deliveryservice.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Product {

    @Id
    private String id;

    @Column(unique = true)
    private String productKey;

    private String name;
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private BigDecimal productValue;

    @JsonIgnore()
    private LocalDateTime createdDate;

    @JsonIgnore()
    private LocalDateTime updatedDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "json")
    private Map<String, String> extra;

    @PrePersist
    public void persist() {
        this.setId(UUID.randomUUID().toString());
        this.setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void update() {
        this.setUpdatedDate(LocalDateTime.now());
    }
}
