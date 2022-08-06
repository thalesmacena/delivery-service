package br.com.example.deliveryservice.infra.queue;

import br.com.example.deliveryservice.domain.internal.dto.DeleteImageEvent;

import java.util.List;

public interface DeleteImageQueueComponent {

    void sendImageToDeleteQueue(DeleteImageEvent image);

    void SendImageListToDeleteQueue(List<DeleteImageEvent> images);
}
