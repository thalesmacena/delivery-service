package br.com.example.deliveryservice.domain.internal.dto;

import br.com.example.deliveryservice.domain.internal.ImageUploadStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResult {

    String name;
    String fileName;
    String keyName;
    String imageUrl;
    ImageUploadStatus status;

}
