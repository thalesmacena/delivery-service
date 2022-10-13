package br.com.example.deliveryservice.domain.services;

import br.com.example.deliveryservice.domain.internal.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductImageService {

    Set<Image> getImagesByProductKey(String productKey);

    Image getImageByProductKeyAndFileName(String fileName, String productKey);

    Set<Image> uploadImagesByProductKey(Set<MultipartFile> files, String productKey);

    void deleteImageByProductKeyAndFileName(String productKey, String name);

    void deleteImagesByProductKeyAndFileNames(String productKey, List<String> names);

    void deleteImagesByProductKeyAndImages(String productKey, List<Image> images);

    void deleteFromBucketByKeyName(String keyName);
}
