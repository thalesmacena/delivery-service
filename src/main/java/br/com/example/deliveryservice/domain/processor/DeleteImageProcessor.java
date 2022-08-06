package br.com.example.deliveryservice.domain.processor;

import br.com.example.deliveryservice.domain.internal.dto.DeleteImageEvent;
import br.com.example.deliveryservice.domain.services.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteImageProcessor implements Processor {

    private final ProductImageService productImageService;

    @Override
    public void process(Exchange exchange) throws Exception {
        DeleteImageEvent payload = exchange.getIn().getBody(DeleteImageEvent.class);

        productImageService.deleteFromBucketByKeyName(payload.getKey());
    }
}
