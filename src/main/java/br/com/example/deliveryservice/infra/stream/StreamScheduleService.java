package br.com.example.deliveryservice.infra.stream;

import br.com.example.deliveryservice.domain.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamScheduleService {

    private final EventService eventService;

    @Value("${amazon.kinesis.streams.delivery-service-product-event-stream.producer-enabled}")
    private boolean scheduleEnabled;

    @Scheduled(cron = "${amazon.kinesis.streams.delivery-service-product-event-stream.producer-cron}", zone = "${amazon.kinesis.streams.delivery-service-product-event-stream.producer-zone}")
    public void scheduledSender() {
        if (scheduleEnabled) {
            log.info("Starting flush events");
            eventService.flushBatchEvents();
        }
    }

}
