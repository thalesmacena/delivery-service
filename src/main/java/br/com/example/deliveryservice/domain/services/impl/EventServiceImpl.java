package br.com.example.deliveryservice.domain.services.impl;

import br.com.example.deliveryservice.domain.internal.Event;
import br.com.example.deliveryservice.domain.internal.Order;
import br.com.example.deliveryservice.domain.services.EventService;
import br.com.example.deliveryservice.infra.repository.EventRepository;
import br.com.example.deliveryservice.infra.stream.StreamComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Value("${amazon.kinesis.streams.delivery-service-product-event-stream.batch-size}")
    private int batchSize;

    private final EventRepository eventRepository;

    private final StreamComponent streamComponent;

    @Override
    public void saveOrderEventToSend(Order order) {
        try {
            Event event = Event.ofOrder(order);

            eventRepository.save(event);
        } catch (Exception ignored) {

        }

    }

    @Transactional
    public void flushBatchEvents() {
        List<Event> events = this.eventRepository.findAvailable(Pageable.ofSize(this.batchSize));

        if (events.isEmpty()) {
            return;
        }

        sendEvents(events);

        List<String> idsToRemove = events.stream().map(Event::getId).toList();

        this.eventRepository.deleteByIdIn(idsToRemove);
    }

    private void sendEvents(List<Event> events) {
        events.forEach(this::sendEvent);
    }

    private void sendEvent(Event event) {
        streamComponent.sendEventStream(event);
    }
}
