package br.com.example.deliveryservice.application.controller;


import br.com.example.deliveryservice.domain.internal.Image;
import br.com.example.deliveryservice.domain.services.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("images")
public class ImageController {

    private final ProductImageService imageService;

    @GetMapping("/key/{productKey}")
    public Set<Image> findImagesByProductKey(@PathVariable String productKey) {
        return imageService.getImagesByProductKey(productKey);
    }

    @GetMapping("/key/{productKey}/name/{fileName}")
    public Image findImageByProductKeyAndFileName(@PathVariable String productKey, @PathVariable String fileName) {
        return this.imageService.getImageByProductKeyAndFileName(fileName, productKey);
    }

    @PostMapping("/key/{productKey}")
    public ResponseEntity<Set<Image>> uploadImagesByProductKey(@PathVariable String productKey, @RequestParam("files") Set<MultipartFile> files) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.imageService.uploadImagesByProductKey(files, productKey));
    }

    @DeleteMapping("/key/{productKey}/name/{fileName}")
    public ResponseEntity<Void> deleteImageByProductKeyAndFileName(@PathVariable String productKey, @PathVariable String fileName) {
        this.imageService.deleteImageByProductKeyAndFileName(productKey, fileName);
        return status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/key/{productKey}")
    public ResponseEntity<Void> deleteImagesByProductKeyAndFiles(@PathVariable String productKey, @RequestParam(value = "files") List<String> filesNames) {
        this.imageService.deleteImagesByProductKeyAndFileNames(productKey, filesNames);
        return status(HttpStatus.NO_CONTENT).build();
    }
}
