package ru.nsu.yattroman.dormsys.service.inerfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;

public interface IEventService {

    Event addEventByClub(Event event, Long clubId);
    Event showEventDetails(Long eventId);
    Page<Event> showEventsPage(Pageable pageable);

}
