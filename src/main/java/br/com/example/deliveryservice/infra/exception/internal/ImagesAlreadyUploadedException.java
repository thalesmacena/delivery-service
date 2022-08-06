package br.com.example.deliveryservice.infra.exception.internal;

import br.com.example.deliveryservice.domain.internal.Image;

import java.util.Set;
import java.util.stream.Collectors;

public class ImagesAlreadyUploadedException extends RuntimeException {

    public ImagesAlreadyUploadedException(Set<Image> images) {
        super(images.stream().map(Image::getFilename).collect(Collectors.joining(", ")));
    }

}
