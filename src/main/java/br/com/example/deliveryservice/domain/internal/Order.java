package br.com.example.deliveryservice.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Order implements Serializable {

    @Id
    private String id;

    @Column(unique = true)
    @NaturalId
    private String orderKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String note;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String username;

    private String address;

    private Long totalInCents;

    private ProductType orderPriority;

    private LocalDateTime deliveryTime;

    private LocalDateTime endDate;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderProduct> products = new ArrayList<>(0);

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
        this.setEntryDate(LocalDateTime.now());
    }

    @PreUpdate
    public void update() {
        this.setUpdatedDate(LocalDateTime.now());
    }

    public void appendNote(String note) {
        this.note = String.format("%s%n%s", this.note, note);
    }

    public void addProduct(Product product, Long quantity) {
        OrderProduct orderProduct = new OrderProduct(this, product, quantity);
        products.add(orderProduct);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            OrderProduct orderProduct = iterator.next();

            if (orderProduct.getOrder().getId().equals(this.id) &&
                    orderProduct.getProduct().getId().equals(product.getId())) {
                iterator.remove();
                orderProduct.setProduct(null);
                orderProduct.setOrder(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Order post = (Order) o;
        return Objects.equals(orderKey, post.orderKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderKey);
    }
}
