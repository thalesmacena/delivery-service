package br.com.example.deliveryservice.domain.internal;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@NaturalIdCache
@Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Product implements Serializable {

    @Id
    private String id;

    @NaturalId
    private String productKey;

    private String name;
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private BigDecimal productValue;

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Image> images = new ArrayList<>(0);

    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate;

    @JsonIgnore
    private LocalDateTime updatedDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> extra;

    @PrePersist
    public void persist() {
        this.setId(isNull(this.getId()) ? UUID.randomUUID().toString() : this.getId());
        this.setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void update() {
        this.setUpdatedDate(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productKey, product.productKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productKey);
    }
}
