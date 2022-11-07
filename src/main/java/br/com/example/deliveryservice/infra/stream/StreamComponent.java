package br.com.example.deliveryservice.infra.stream;

import br.com.example.deliveryservice.domain.internal.Event;

public interface StreamComponent {

    void sendEventStream(Event event);
}
