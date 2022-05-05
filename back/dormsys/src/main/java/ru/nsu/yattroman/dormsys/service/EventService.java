package ru.nsu.yattroman.dormsys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.repository.ClubRepository;
import ru.nsu.yattroman.dormsys.repository.EventRepository;
import ru.nsu.yattroman.dormsys.service.inerfaces.IEventService;

@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;

    public EventService(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public Event addEventByClub(Event event, Long clubId) {
        var club = clubRepository.findClubById(clubId);

        if(event == null || club == null){
            return null;
        }

        event.setClub(club);

        return eventRepository.save(event);
    }

    @Override
    public Event showEventDetails(Long eventId) {
        return eventRepository.findEventById(eventId);
    }

    @Override
    public Page<Event> showEventsPage(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

}
