package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.internal.dto.FileUploadResult;
import br.com.example.deliveryservice.domain.internal.ImageUploadStatus;
import br.com.example.deliveryservice.domain.services.ImageBucketService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class ImageBucketServiceImpl implements ImageBucketService {

    @Value("${amazon.region}")
    String region;

    @Value("${amazon.s3.object-url-formatter}")
    String s3ObjectUrlFormatter;

    @Value("${amazon.s3.buckets.delivery-image-bucket.url}")
    private String endpoint;

    @Value("${amazon.s3.buckets.delivery-image-bucket.config}")
    private String config;

    private final ConsumerTemplate consumerTemplate;

    private final ProducerTemplate producerTemplate;

    @Override
    public FileUploadResult uploadImage(MultipartFile file, String keyName) {
        String uploadConfig = String.format("&keyName=%s", keyName);
        String formattedEndpoint = this.formatEndpoint(uploadConfig);

        try {
            producerTemplate.sendBody(formattedEndpoint, file.getBytes());

            String imageUrl = this.formatProductImageUrl(keyName);

            return FileUploadResult.builder()
                    .name(formatName(Objects.requireNonNull(file.getOriginalFilename())))
                    .fileName(file.getOriginalFilename())
                    .keyName(keyName)
                    .imageUrl(imageUrl)
                    .status(ImageUploadStatus.UPLOADED)
                    .build();
        } catch (Exception e) {
            return FileUploadResult.builder()
                    .name(formatName(Objects.requireNonNull(file.getOriginalFilename())))
                    .fileName(file.getOriginalFilename())
                    .keyName(keyName)
                    .status(ImageUploadStatus.FAILED)
                    .build();
        }
    }

    private String formatEndpoint(String options) {
        String formattedOptions = this.formatOptions(options);

        return this.endpoint.concat(formattedOptions);
    }

    private String formatOptions(String options) {
        return this.config.concat(options);
    }

    private String formatName(String originalFileName) {
        return originalFileName.substring(0, originalFileName.lastIndexOf('.'));
    }

    public Object getImage(String fileNamePrefix) {
        String fileNameConfig = "&prefix=".concat(fileNamePrefix);

        String formattedEndpoint = this.formatEndpoint(fileNameConfig);

        return this.consumerTemplate.receiveBodyNoWait(formattedEndpoint);
    }

    public String formatProductImageUrl(String keyName) {
        return String.format(this.s3ObjectUrlFormatter, getBucketName(), this.region, keyName);
    }

    @Override
    public void deleteImage(String keyName) {
        String deleteConfig = String.format("&keyName=%s&operation=deleteObject", keyName);
        String formattedEndpoint = this.formatEndpoint(deleteConfig);

        producerTemplate.sendBody(formattedEndpoint, keyName);
    }

    private String getBucketName() {
        return this.endpoint.replace("aws2-s3://", "");
    }
}
