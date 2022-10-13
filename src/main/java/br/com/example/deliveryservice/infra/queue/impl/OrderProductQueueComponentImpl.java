package br.com.example.deliveryservice.infra.queue.impl;

import static br.com.example.deliveryservice.infra.util.AwsUtils.concatUrlWithConfig;

import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.infra.queue.OrderProductQueueComponent;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProductQueueComponentImpl implements OrderProductQueueComponent {

    @Value("${amazon.sqs.queues.delivery-service-order-queue.url}")
    private String endpoint;

    @Value("${amazon.sqs.queues.delivery-service-order-queue.config}")
    private String config;

    private final ProducerTemplate producerTemplate;

    private final Gson objectMapper;

    @Override
    public void sendOrderToOrderProcessQueue(OrderPayload order) {
        String formattedEndpoint = concatUrlWithConfig(endpoint, config);

        producerTemplate.sendBody(formattedEndpoint, objectMapper.toJson(order));
    }
}
