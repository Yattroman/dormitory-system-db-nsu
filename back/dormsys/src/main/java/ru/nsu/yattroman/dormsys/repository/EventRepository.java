package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.yattroman.dormsys.entity.Event;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventById(Long id);
    List<Event> findEventsByClub_Id(Long clubId);
    List<Event> findEventsByParticipantsId(Long participantId);
    List<Event> findEventsByClub_IdAndTakeTimeAfter(Long clubId, Date time);
}
