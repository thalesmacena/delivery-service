package br.com.example.deliveryservice.infra.route;

import br.com.example.deliveryservice.domain.internal.dto.DeleteImageEvent;
import br.com.example.deliveryservice.domain.processor.DeleteImageProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.example.deliveryservice.infra.util.AwsUtils.concatUrlWithConfig;

@Component
@RequiredArgsConstructor
public class DeleteImageRouteBuilder extends RouteBuilder {

    @Value("${amazon.sqs.queues.delivery-service-delete-image-queue.url}")
    private String endpoint;

    @Value("${amazon.sqs.queues.delivery-service-delete-image-queue.config}")
    private String config;

    private final DeleteImageProcessor deleteImageProcessor;

    @Override
    public void configure() {
        String formattedEndpoint = concatUrlWithConfig(endpoint, config);

        from(formattedEndpoint)
                .split(body())
                .unmarshal()
                .json(JsonLibrary.Jackson, DeleteImageEvent.class)
                .process(deleteImageProcessor);

    }
}