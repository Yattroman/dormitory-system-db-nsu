package ru.nsu.yattroman.dormsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.EventDto;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.mapper.EventMapper;
import ru.nsu.yattroman.dormsys.service.EventService;

import java.util.HashMap;
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

    @PostMapping(value = "event")
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventDto){

        var event = eventMapper.toEntity(eventDto);
//        eventService.addEventByClub(event, eventDto.getClub().getId());
        eventService.addEventByClub(event, 290L);

        return ResponseEntity
                .ok()
                .body("Club successfully added.");
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
    public ResponseEntity<?> showEvents(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){

        Pageable paging = PageRequest.of(page, size);
        var resultPage = eventService.showEventsPage(paging);

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

}
