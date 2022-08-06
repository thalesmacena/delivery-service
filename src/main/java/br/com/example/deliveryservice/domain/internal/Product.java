package br.com.example.deliveryservice.domain.internal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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

    @OneToMany(mappedBy = "product")
    private List<Image> images;

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
        this.setId(UUID.randomUUID().toString());
        this.setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void update() {
        this.setUpdatedDate(LocalDateTime.now());
    }
}
