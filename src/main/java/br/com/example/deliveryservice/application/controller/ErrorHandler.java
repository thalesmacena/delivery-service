package br.com.example.deliveryservice.application.controller;

import static br.com.example.deliveryservice.infra.util.ValidationUtils.handleValidationMessage;

import br.com.example.deliveryservice.domain.internal.dto.error.ErrorMessage;
import br.com.example.deliveryservice.domain.internal.dto.error.ErrorType;
import br.com.example.deliveryservice.domain.services.MessageContextService;
import br.com.example.deliveryservice.infra.exception.external.AuthenticationServiceException;
import br.com.example.deliveryservice.infra.exception.external.UnauthenticatedException;
import br.com.example.deliveryservice.infra.exception.external.UnauthorizedException;
import br.com.example.deliveryservice.infra.exception.internal.ImageNotFoundException;
import br.com.example.deliveryservice.infra.exception.internal.ImagesAlreadyUploadedException;
import br.com.example.deliveryservice.infra.exception.internal.IncorrectFileContentTypeException;
import br.com.example.deliveryservice.infra.exception.internal.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.secure.spi.IntegrationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final MessageContextService messageContextService;

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleProductNotFoundException(Exception e) {
        log.error(messageContextService.getMessage("product-not-found-exception"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("product-not-found-exception.message", e.getMessage()), ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(IncorrectFileContentTypeException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectFileContentTypeException(Exception e) {
        log.error(messageContextService.getMessage("image-incorrect-content-exception"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("image-incorrect-content-exception.message", e.getMessage()), ErrorType.BAD_REQUEST));
    }


    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleImageNotFoundException(Exception e) {
        log.error(messageContextService.getMessage("image-not-found-exception"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("image-not-found-exception.message", e.getMessage()), ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(ImagesAlreadyUploadedException.class)
    public ResponseEntity<ErrorMessage> handleImagesAlreadyUploadedException(Exception e) {
        log.error(messageContextService.getMessage("image-already-uploaded-exception"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("image-already-uploaded-exception.message", e.getMessage()), ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handleUnauthorizedException(Exception e) {
        log.error(messageContextService.getMessage("unauthorized-exception"), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildResponseMessage(messageContextService.getMessage("unauthorized-exception.message", e.getMessage()), ErrorType.UNAUTHORIZED));
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<ErrorMessage> handleUnauthenticatedException(Exception e) {
        log.error(messageContextService.getMessage("unauthenticated-exception"), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildResponseMessage(messageContextService.getMessage("unauthenticated-exception.message", e.getMessage()), ErrorType.UNAUTHORIZED));
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationServiceException(Exception e) {
        log.error(messageContextService.getMessage("authentication-service-exception"), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildResponseMessage(messageContextService.getMessage("authentication-service-exception.message", e.getMessage()), ErrorType.INTERNAL_SERVER_ERROR));
    }


    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorMessage> handleIntegrationException(Exception e) {
        log.error(messageContextService.getMessage("integration-exception"), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildResponseMessage(messageContextService.getMessage("integration-exception.message", e.getMessage()), ErrorType.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(Exception e) {
        log.error(messageContextService.getMessage("not-readable-exception"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("not-readable-exception.message"), ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(messageContextService.getMessage("validation-exception.fields"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("validation-exception.fields.message", handleValidationMessage(e.getFieldErrors())), ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error(messageContextService.getMessage("validation-exception.media-type"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("validation-exception.media-type.message", e.getMessage()), ErrorType.BAD_REQUEST));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(messageContextService.getMessage("database-exception.data-integrity"), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildResponseMessage(messageContextService.getMessage("database-exception.data-integrity.message", e.getMessage()), ErrorType.BAD_REQUEST));
    }

    private ErrorMessage buildResponseMessage(String message, ErrorType code) {
        return ErrorMessage.builder()
                .message(message)
                .code(code)
                .build();
    }
}
