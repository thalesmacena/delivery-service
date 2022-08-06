package br.com.example.deliveryservice.domain.adpter.impl;

import br.com.example.deliveryservice.domain.adpter.ImageMapper;
import br.com.example.deliveryservice.domain.internal.Image;
import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.internal.dto.FileUploadResult;
import org.springframework.stereotype.Service;

@Service
public class ImageMapperImpl implements ImageMapper {

    @Override
    public Image toEntity(FileUploadResult fileUploadResult, Product product) {
        return Image.builder()
                .name(fileUploadResult.getName())
                .filename(fileUploadResult.getFileName())
                .key(fileUploadResult.getKeyName())
                .url(fileUploadResult.getImageUrl())
                .status(fileUploadResult.getStatus())
                .product(product)
                .build();
    }
}
