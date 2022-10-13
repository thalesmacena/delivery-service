package br.com.example.deliveryservice.infra.route;

import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.processor.OrderProductProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static br.com.example.deliveryservice.infra.util.AwsUtils.concatUrlWithConfig;

@Component
@RequiredArgsConstructor
public class OrderProductQueueRouteBuilder extends RouteBuilder {

    @Value("${amazon.sqs.queues.delivery-service-order-queue.url}")
    private String endpoint;

    @Value("${amazon.sqs.queues.delivery-service-order-queue.config}")
    private String config;

    private final OrderProductProcessor orderProductProcessor;

    @Override
    public void configure() {
        String formattedEndpoint = concatUrlWithConfig(endpoint, config);

        from(formattedEndpoint)
                .unmarshal()
                .json(JsonLibrary.Jackson, OrderPayload.class)
                .process(orderProductProcessor);

    }
}
