package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nsu.yattroman.dormsys.DTO.EventDto;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.DTO.metainfo.EventBoardElement;
import ru.nsu.yattroman.dormsys.entity.Event;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventById(Long id);
    List<Event> findEventsByClub_Id(Long clubId);
    List<Event> findEventsByParticipantsId(Long participantId);
    List<Event> findEventsByClub_IdAndTakeTimeAfter(Long clubId, Date time);
    @Query(value = "select new ru.nsu.yattroman.dormsys.DTO.metainfo.EventBoardElement(" +
            "e.id, e.name, e.location, e.takeTime, count(p), c.name)" +
            "from Event e " +
            "inner join e.club c " +
            "left outer join e.participants p " +
            "group by e, c order by count(p) desc")
    List<EventBoardElement> findAllEventsWithParticipantsInfo();
}
