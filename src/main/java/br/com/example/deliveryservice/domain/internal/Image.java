package br.com.example.deliveryservice.domain.internal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    private String id;

    private String name;

    private String filename;

    @Column(unique = true)
    @NaturalId
    private String key;

    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @JsonIgnore
    private LocalDateTime createdDate;

    @PrePersist
    public void persist() {
        this.setId(UUID.randomUUID().toString());
        this.setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    private void update() {
        throw new UnsupportedOperationException();
    }

    @Transient
    @Enumerated(EnumType.STRING)
    private ImageUploadStatus status;

    @Transient
    public ImageUploadStatus getStatus() {
        return Objects.isNull(this.status) ? ImageUploadStatus.UPLOADED : this.status;
    }
}
