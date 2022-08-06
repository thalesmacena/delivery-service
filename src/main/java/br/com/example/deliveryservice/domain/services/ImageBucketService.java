package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.internal.dto.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImageBucketService {

    FileUploadResult uploadImage(MultipartFile file, String keyName);

    Object getImage(String fileNamePrefix);

    String formatProductImageUrl(String keyName);

    void deleteImage(String keyName);
}
