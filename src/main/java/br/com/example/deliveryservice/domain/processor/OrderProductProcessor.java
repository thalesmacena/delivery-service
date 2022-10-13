package br.com.example.deliveryservice.domain.processor;

import br.com.example.deliveryservice.domain.internal.dto.OrderPayload;
import br.com.example.deliveryservice.domain.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProductProcessor implements Processor {

    private final OrderService orderService;

    @Override
    public void process(Exchange exchange) {
        OrderPayload payload = exchange.getIn().getBody(OrderPayload.class);

        orderService.processPayload(payload);
    }
}
