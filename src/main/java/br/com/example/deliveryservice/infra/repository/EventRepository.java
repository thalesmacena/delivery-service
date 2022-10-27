package br.com.example.deliveryservice.infra.repository;

import br.com.example.deliveryservice.domain.internal.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT e FROM Event e")
    List<Event> findAvailable(Pageable p);

    void deleteByIdIn(List<String> ids);

}
