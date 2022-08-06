package br.com.example.deliveryservice.infra.queue.impl;

import static br.com.example.deliveryservice.infra.util.AwsUtils.concatUrlWithConfig;

import br.com.example.deliveryservice.domain.internal.dto.DeleteImageEvent;
import br.com.example.deliveryservice.infra.queue.DeleteImageQueueComponent;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteImageQueueComponentImpl implements DeleteImageQueueComponent {
    @Value("${amazon.sqs.queues.delivery-service-delete-image-queue.url}")
    private String endpoint;

    @Value("${amazon.sqs.queues.delivery-service-delete-image-queue.config}")
    private String config;

    private final ProducerTemplate producerTemplate;

    private final Gson objectMapper;

    @Override
    public void sendImageToDeleteQueue(DeleteImageEvent image) {
        String formattedEndpoint = concatUrlWithConfig(endpoint, config);

        producerTemplate.sendBody(formattedEndpoint, objectMapper.toJson(image));
    }

    @Override
    public void SendImageListToDeleteQueue(List<DeleteImageEvent> images) {
        String formattedEndpoint = concatUrlWithConfig(endpoint, config);

        List<String> jsonObjectList= images.stream().map(objectMapper::toJson).toList();

        producerTemplate.sendBody(formattedEndpoint, jsonObjectList);
    }
}
