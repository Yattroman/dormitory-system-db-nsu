package ru.nsu.yattroman.dormsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.EventDto;
import ru.nsu.yattroman.dormsys.controller.request.EventEnrollRequest;
import ru.nsu.yattroman.dormsys.controller.specification.EventSpecificationBuilder;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.mapper.EventMapper;
import ru.nsu.yattroman.dormsys.service.EventService;
import ru.nsu.yattroman.dormsys.util.SortType;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/leisure/")
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @Autowired
    public EventController(EventMapper eventMapper, EventService eventService) {
        this.eventMapper = eventMapper;
        this.eventService = eventService;
    }

    @PostMapping(value = "event/club")
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventDto){

        System.out.println(eventDto.toString());
        var event = eventMapper.toEntity(eventDto);
        eventService.addEventByClub(event, event.getClub().getId());

        return ResponseEntity
                .ok()
                .body("Event successfully added.");
    }

    @GetMapping(value = "event/{eventId}")
    public ResponseEntity<?> showEventDetails(@PathVariable Long eventId){

        var event = eventService.showEventDetails(eventId);

        if(event == null){
            return ResponseEntity
                    .badRequest()
                    .body("There is no event with given id");
        }

        return ResponseEntity
                .ok()
                .body(eventMapper.toDTO(event));
    }

    @GetMapping(value = "events")
    public ResponseEntity<?> showEvents(HttpServletRequest request){
        var page = request.getParameter("page") != null ?
                Integer.parseInt(request.getParameter("page")) : 0;
        var size = request.getParameter("size") != null ?
                Integer.parseInt(request.getParameter("size")) : 10;
        var sortField = request.getParameter("sortField") != null?
                request.getParameter("sortField") : "name";
        var sortType = request.getParameter("sortType") != null
                && request.getParameter("sortType").equals("asc") ?
                SortType.ASC : SortType.DESC;
        var searchParams = request.getParameter("search");

        EventSpecificationBuilder builder = new EventSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(searchParams + ",");
        while (matcher.find()) {
            System.out.println(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Event> specification = builder.build();

        Pageable paging;
        if(sortType == SortType.DESC){
            paging = PageRequest.of(page, size, Sort.by(sortField).descending());
        } else {
            paging = PageRequest.of(page, size, Sort.by(sortField).ascending());
        }

        var resultPage = eventService.showEventsPageFiltered(specification, paging);
        var events = resultPage.getContent().stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
        HashMap<String, Object> response = new HashMap<>();
        response.put("events", events);
        response.put("currentPage", resultPage.getNumber());
        response.put("totalItems", resultPage.getTotalElements());
        response.put("totalPages", resultPage.getTotalPages());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("event/participant")
    public ResponseEntity<?> enrollToEvent(@RequestBody EventEnrollRequest request){

        eventService.enrollUserToEvent(request.getEventId(), request.getUserId());

        return ResponseEntity
                .ok()
                .body("User successfully enrolled to event");
    }

    @DeleteMapping("event/participant")
    public ResponseEntity<?> unenrollFromEvent(HttpServletRequest request){
        var eventId = request.getParameter("eventId");
        var userId = request.getParameter("userId");

        eventService.unenrollUserFromEvent(Long.parseLong(eventId), Long.parseLong(userId));

        return ResponseEntity
                .ok()
                .body("User successfully unenrolled from event");
    }

    private HashMap<?, ?> wrapEventsToResponse(List<Event> events){
        var userEvents = events.stream().map(eventMapper::toDTO).collect(Collectors.toList());

        HashMap<String, Object> response = new HashMap<>();

        response.put("events", userEvents);
        response.put("eventsNumber", userEvents.size());

        return response;
    }

    @GetMapping("events/user")
    public ResponseEntity<?> getEventsByUser(HttpServletRequest request){
        var userId = request.getParameter("userId");

        var response = wrapEventsToResponse(eventService.getEventsByUser(Long.parseLong(userId)));

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("events/club")
    public ResponseEntity<?> getEventsByClub(HttpServletRequest request){
        var userId = request.getParameter("clubId");

        var response = wrapEventsToResponse(eventService.getEventsByClub(Long.parseLong(userId)));

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("events/club/upcoming")
    public ResponseEntity<?> getUpcomingEventsByClub(HttpServletRequest request){
        var clubId = Long.parseLong(request.getParameter("clubId"));
        var response = wrapEventsToResponse(eventService.getUpcomingEventsByClub(clubId));

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("events/top")
    public ResponseEntity<?> getTopPopularEvents(HttpServletRequest request){
        var n = Integer.parseInt(request.getParameter("n"));
        var participantsMin = Long.parseLong(request.getParameter("participantsMin"));
        var participantsMax = Long.parseLong(request.getParameter("participantsMax"));

        return ResponseEntity
                .ok()
                .body(eventService.getTopPopularEventsWithParticipantsRange(n, participantsMin, participantsMax));
    }

    @GetMapping("events/closest")
    public ResponseEntity<?> getClosestEvents(){
        return ResponseEntity
                .ok()
                .body(eventService.getClosestEvents());
    }
}
