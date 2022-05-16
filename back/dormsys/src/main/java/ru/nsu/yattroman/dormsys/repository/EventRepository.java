package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.nsu.yattroman.dormsys.DTO.metainfo.ClubEventsAvg;
import ru.nsu.yattroman.dormsys.DTO.metainfo.EventBoardElement;
import ru.nsu.yattroman.dormsys.entity.Event;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Event findEventById(Long id);
    List<Event> findEventsByClub_Id(Long clubId);
    List<Event> findEventsByParticipantsId(Long participantId);
    List<Event> findEventsByClub_IdAndTakeTimeAfter(Long clubId, Date time);
    @Query(value = "select new ru.nsu.yattroman.dormsys.DTO.metainfo.EventBoardElement(" +
            "e.id, e.name, e.location, function('to_char', e.takeTime, 'DD-MM-YYYY'), count(p), c.name)" +
            "from Event e " +
            "inner join e.club c " +
            "left outer join e.participants p " +
            "group by e, c " +
            "having count(p) > :participantsMin and count(p) < :participantsMax " +
            "order by count(p) desc" )
    List<EventBoardElement> findAllEventsWithParticipantsInfo(
            @Param("participantsMin") Long participantsMin,
            @Param("participantsMax") Long participantsMax
    );
    @Query(value = "select new ru.nsu.yattroman.dormsys.DTO.metainfo.EventBoardElement(" +
            "e.id, e.name, e.location, function('to_char', e.takeTime, 'DD-MM-YYYY'), count(p), c.name) " +
            "from Event e " +
            "inner join e.club c " +
            "left outer join e.participants p " +
            "where e.takeTime = (select min(e.takeTime) from Event e) " +
            "group by e, c")
    List<EventBoardElement> getСlosestEvents();
}
