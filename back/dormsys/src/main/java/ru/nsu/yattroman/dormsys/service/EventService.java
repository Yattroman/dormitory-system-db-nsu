package ru.nsu.yattroman.dormsys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.DTO.metainfo.EventBoardElement;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.repository.ClubRepository;
import ru.nsu.yattroman.dormsys.repository.EventRepository;
import ru.nsu.yattroman.dormsys.repository.UserRepository;
import ru.nsu.yattroman.dormsys.service.inerfaces.IEventService;
import ru.nsu.yattroman.dormsys.util.Connect;

import java.util.Date;
import java.util.List;

@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, ClubRepository clubRepository,
                        UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
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

    private void manipulateUserAndEventConnection(Long eventId, Long userId, Connect connection){
        var event = eventRepository.findEventById(eventId);
        var user = userRepository.findUserById(userId);

        if(user == null || event == null){
            //TODO: add exception no event or user
            return;
        }

        if(connection.equals(Connect.CONNECT)){
            user.addEvent(event);
        } else if(connection.equals(Connect.DISCONNECT)){
            user.removeEvent(event);
        }
        userRepository.save(user);
    }


    @Override
    public void enrollUserToEvent(Long eventId, Long userId) {
        manipulateUserAndEventConnection(eventId, userId, Connect.CONNECT);
    }

    @Override
    public void unenrollUserFromEvent(Long eventId, Long userId) {
        manipulateUserAndEventConnection(eventId, userId, Connect.DISCONNECT);
    }

    @Override
    public List<Event> getEventsByUser(Long userId) {
        return eventRepository.findEventsByParticipantsId(userId);
    }

    @Override
    public List<Event> getEventsByClub(Long clubId) {
        return eventRepository.findEventsByClub_Id(clubId);
    }

    @Override
    public List<Event> getUpcomingEventsByClub(Long clubId) {
        return eventRepository.findEventsByClub_IdAndTakeTimeAfter(clubId, new Date());
    }

    @Override
    public List<EventBoardElement> getTopPopularEvents(int n) {
        var allEvents = eventRepository.findAllEventsWithParticipantsInfo();
        return allEvents.size() <= n ? allEvents.subList(0, allEvents.size()) : allEvents.subList(0, n);
    }
}
