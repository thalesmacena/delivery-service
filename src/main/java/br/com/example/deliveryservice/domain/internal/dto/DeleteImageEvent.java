package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.Image;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class DeleteImageEvent {

    private String key;

    public DeleteImageEvent(Image image) {
        this.key = image.getKey();
    }

    public DeleteImageEvent(String key) {
        this.key = key;
    }

}
