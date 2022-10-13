package br.com.example.deliveryservice.domain.adapter;

import br.com.example.deliveryservice.domain.internal.Image;
import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.internal.dto.FileUploadResult;

public interface ImageMapper {

    Image toEntity(FileUploadResult fileUploadResult, Product product);

}
