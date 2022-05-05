package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.yattroman.dormsys.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    Event findEventById(Long id);

}
