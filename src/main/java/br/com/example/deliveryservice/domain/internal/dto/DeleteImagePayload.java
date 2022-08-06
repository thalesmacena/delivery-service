package br.com.example.deliveryservice.domain.internal.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteImagePayload {
    private String name;
    private String filename;
    private String key;
    private String url;
}
