package br.com.example.deliveryservice.domain.internal;

import static br.com.example.deliveryservice.infra.util.MapperUtils.createMapper;

import br.com.example.deliveryservice.domain.internal.dto.EventDataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    private String id;

    @Lob
    @Column(length=1000)
    private String data;

    private LocalDateTime eventDate;

    public static Event ofOrder(Order order) throws JsonProcessingException {
        ObjectMapper mapper = createMapper();

        EventDataDTO data = EventDataDTO.ofOrder(order);

        return Event.builder()
                .id(UUID.randomUUID().toString())
                .eventDate(LocalDateTime.now())
                .data(mapper.writeValueAsString(data))
                .build();

    }

}
