package br.com.example.deliveryservice.infra.stream.impl;

import br.com.example.deliveryservice.domain.internal.Event;
import br.com.example.deliveryservice.infra.stream.StreamComponent;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static br.com.example.deliveryservice.infra.util.AwsUtils.concatUrlWithConfig;

@Component
@RequiredArgsConstructor
public class StreamComponentImpl implements StreamComponent {

    @Value("${amazon.kinesis.streams.delivery-service-product-event-stream.url}")
    private String endpoint;

    @Value("${amazon.kinesis.streams.delivery-service-product-event-stream.config}")
    private String config;

    private final ProducerTemplate producerTemplate;

    @Override
    public void sendEventStream(Event event) {
        String formattedEndpoint = concatUrlWithConfig(endpoint, config);

        producerTemplate.asyncSendBody(formattedEndpoint, event);
    }
}
