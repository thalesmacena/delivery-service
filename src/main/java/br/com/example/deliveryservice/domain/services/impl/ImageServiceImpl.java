package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.adpter.ImageMapper;
import br.com.example.deliveryservice.domain.internal.Image;
import br.com.example.deliveryservice.domain.internal.ImageUploadStatus;
import br.com.example.deliveryservice.domain.internal.Product;
import br.com.example.deliveryservice.domain.internal.dto.DeleteImageEvent;
import br.com.example.deliveryservice.domain.internal.dto.DeleteImagePayload;
import br.com.example.deliveryservice.domain.internal.dto.FileUploadResult;
import br.com.example.deliveryservice.domain.services.ImageBucketService;
import br.com.example.deliveryservice.domain.services.ProductImageService;
import br.com.example.deliveryservice.domain.services.ProductService;
import br.com.example.deliveryservice.infra.exception.internal.ImageNotFoundException;
import br.com.example.deliveryservice.infra.exception.internal.ImagesAlreadyUploadedException;
import br.com.example.deliveryservice.infra.exception.internal.IncorrectFileContentTypeException;
import br.com.example.deliveryservice.infra.exception.internal.ProductNotFoundException;
import br.com.example.deliveryservice.infra.queue.DeleteImageQueueComponent;
import br.com.example.deliveryservice.infra.repository.ImageRepository;
import br.com.example.deliveryservice.infra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ProductImageService {

    private final ImageBucketService imageBucketService;

    private final DeleteImageQueueComponent deleteImageQueueComponent;

    private final ProductRepository productRepository;

    private final ImageMapper imageMapper;

    private final ImageRepository imageRepository;

    @Override
    public Set<Image> getImagesByProductKey(String productKey) {
        return imageRepository.findByProductProductKey(productKey);
    }

    @Override
    public Image getImageByProductKeyAndFileName(String fileName, String productKey) {
        Product product = this.productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        String keyName = this.generateProductImageObjectKeyName(fileName, product.getProductKey(), product.getProductType().name());

        return imageRepository.findByProductProductKeyAndKey(productKey, keyName).orElseThrow(() -> new ImageNotFoundException(productKey, fileName));
    }

    @Override
    public Set<Image> uploadImagesByProductKey(Set<MultipartFile> files, String productKey) {
        if (!isAllFilesContentTypeImages(files)) {
            throw new IncorrectFileContentTypeException();
        }

        Product product = productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        List<FileUploadResult> uploadedResultFiles = files.stream().map(file -> uploadFile(file, product)).toList();

        return saveImages(uploadedResultFiles.stream().map(fileResult -> imageMapper.toEntity(fileResult, product)).toList());
    }

    private boolean isAllFilesContentTypeImages(Set<MultipartFile> files) {
        return files.stream().allMatch(this::isFileContentTypeImage);
    }

    private boolean isFileContentTypeImage(MultipartFile file) {
        return Objects.requireNonNull(file.getContentType()).startsWith("image");
    }

    private FileUploadResult uploadFile(MultipartFile file, Product product) {
        String keyName = this.generateProductImageObjectKeyName(file.getOriginalFilename(), product.getProductKey(), product.getProductType().name());

        return imageBucketService.uploadImage(file, keyName);
    }

    private String generateProductImageObjectKeyName(String fileName, String productKey, String productType) {
        return String.format("%s/%s/%s", productType, productKey, fileName);
    }

    private Set<Image> saveImages(List<Image> images) {
        Set<Image> uploadedImages = getUploadedImages(images);

        try {
            Set<Image> savedImages = new HashSet<>(imageRepository.saveAll(uploadedImages));

            images.forEach(image -> {
                if (ImageUploadStatus.FAILED.equals(image.getStatus())) {
                    savedImages.add(image);
                }
            });

            return savedImages;
        } catch (DataIntegrityViolationException e) {
            deleteImageQueueComponent.SendImageListToDeleteQueue(uploadedImages.stream().map(DeleteImageEvent::new).toList());

            throw new ImagesAlreadyUploadedException(uploadedImages);

        } catch (Exception e) {
            deleteImageQueueComponent.SendImageListToDeleteQueue(uploadedImages.stream().map(DeleteImageEvent::new).toList());

            throw e;
        }
    }

    private Set<Image> getUploadedImages(List<Image> images) {
        return images.stream().filter(image -> ImageUploadStatus.UPLOADED.equals(image.getStatus())).collect(Collectors.toSet());
    }

    @Override
    public void deleteImageByProductKeyAndFileName(String productKey, String fileName) {
        Product product = this.productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        String keyName = this.generateProductImageObjectKeyName(fileName, product.getProductKey(), product.getProductType().name());

        Image existentImage = imageRepository.findByProductProductKeyAndKey(productKey, keyName).orElseThrow(() -> new ImageNotFoundException(productKey, fileName));

        deleteImageQueueComponent.sendImageToDeleteQueue(new DeleteImageEvent(existentImage));

        imageRepository.delete(existentImage);
    }

    @Override
    public void deleteImagesByProductKeyAndFileNames(String productKey, List<String> filesNames) {
        Product product = this.productRepository.findByProductKey(productKey).orElseThrow(() -> new ProductNotFoundException(productKey));

        List<String> keys = filesNames.stream().map(fileName ->
                this.generateProductImageObjectKeyName(fileName, product.getProductKey(), product.getProductType().name())
        ).toList();

        Set<Image> existentImages = imageRepository.findByProductProductKeyAndKeyIn(productKey, keys);

        deleteImageQueueComponent.SendImageListToDeleteQueue(existentImages.stream().map(DeleteImageEvent::new).toList());

        imageRepository.deleteAll(existentImages);
    }

    @Override
    public void deleteImagesByProductKeyAndImages(String productKey, List<Image> images) {
        List<String> keys = images.stream().map(Image::getKey).toList();

        Set<Image> existentImages = imageRepository.findByProductProductKeyAndKeyIn(productKey, keys);

        deleteImageQueueComponent.SendImageListToDeleteQueue(existentImages.stream().map(DeleteImageEvent::new).toList());

        imageRepository.deleteAll(existentImages);
    }

    public void deleteFromBucketByKeyName(String keyName) {
        imageBucketService.deleteImage(keyName);
    }


}
