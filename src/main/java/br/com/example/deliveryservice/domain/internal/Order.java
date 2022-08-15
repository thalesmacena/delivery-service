package br.com.example.deliveryservice.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Order {

    @Id
    private String id;

    private String orderKey;

    private OrderStatus status;

    private String note;

    private PaymentMethod paymentMethod;

    private String username;

    private String address;

    private Long totalInCents;

    private LocalDateTime deliveryTime;

    private LocalDateTime endDate;

    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime entryDate;

    @JsonIgnore
    private LocalDateTime updatedDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, String> extra;

    @PrePersist
    public void persist() {
        this.setId(UUID.randomUUID().toString());
        this.setEntryDate(LocalDateTime.now());
    }

    @PreUpdate
    public void update() {
        this.setUpdatedDate(LocalDateTime.now());
    }
}
