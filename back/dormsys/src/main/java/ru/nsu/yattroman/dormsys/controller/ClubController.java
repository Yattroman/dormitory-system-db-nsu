package ru.nsu.yattroman.dormsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.controller.request.ClubSubscribeRequest;
import ru.nsu.yattroman.dormsys.entity.Event;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.mapper.ClubMapper;
import ru.nsu.yattroman.dormsys.service.inerfaces.IClubService;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leisure/")
public class ClubController {

    private final ClubMapper clubMapper;
    private final IClubService clubService;

    @Autowired
    public ClubController(ClubMapper clubMapper, IClubService clubService) {
        this.clubMapper = clubMapper;
        this.clubService = clubService;
    }

    @PostMapping("club")
    public ResponseEntity<?> addClub(@RequestBody ClubDto clubDto) {

        var club = clubMapper.toEntity(clubDto);

        clubService.addClub(club);

        return ResponseEntity
                .ok()
                .body("Club successfully added.");
    }

    @GetMapping("club/{clubId}")
    public ResponseEntity<?> showClubDetails(@PathVariable Long clubId) {

        var club = clubService.showClubDetails(clubId);

        if (club == null) {
            return ResponseEntity
                    .badRequest()
                    .body("There is no club with given id");
        }

        return ResponseEntity
                .ok()
                .body(clubMapper.toDTO(club));
    }

    @GetMapping("clubs")
    public ResponseEntity<?> showClubs(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "6") int size) {

        Pageable paging = PageRequest.of(page, size);
        var resultPage = clubService.showClubsPage(paging);

        var clubs = resultPage.getContent().stream()
                .map(clubMapper::toDTO).collect(Collectors.toList());

        HashMap<String, Object> response = new HashMap<>();

        response.put("clubs", clubs);
        response.put("currentPage", resultPage.getNumber());
        response.put("totalItems", resultPage.getTotalElements());
        response.put("totalPages", resultPage.getTotalPages());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("club/participant")
    public ResponseEntity<?> subscribeToClub(@RequestBody ClubSubscribeRequest request) {

        clubService.subscribeUserToClub(request.getClubId(), request.getUserId());

        return ResponseEntity
                .ok()
                .body("Use successfully subscribed to club");
    }

    @DeleteMapping("club/participant")
    public ResponseEntity<?> unsubscribeFromClub(HttpServletRequest request) {
        var clubId = request.getParameter("clubId");
        var userId = request.getParameter("userId");

        clubService.unsubscribeUserFromClub(Long.parseLong(clubId), Long.parseLong(userId));

        return ResponseEntity
                .ok()
                .body("Use successfully unsubscribed from club");
    }

    private HashMap<?, ?> wrapClubsToResponse(List<Club> clubs) {
        var userClubs = clubs.stream().map(clubMapper::toDTO).collect(Collectors.toList());

        HashMap<String, Object> response = new HashMap<>();

        response.put("clubs", userClubs);
        response.put("clubsNumber", userClubs.size());

        return response;
    }

    @GetMapping("clubs/user")
    public ResponseEntity<?> getClubsByUser(HttpServletRequest request) {
        var userId = request.getParameter("userId");
        var response = wrapClubsToResponse(clubService.getClubsByUser(Long.parseLong(userId)));

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("clubs/managing")
    public ResponseEntity<?> getUserManagingClubs(HttpServletRequest request) {
        var userId = request.getParameter("userId");
        var response = wrapClubsToResponse(clubService.getClubsByClubManager(Long.parseLong(userId)));

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("clubs/top/{n}")
    public ResponseEntity<?> getTopPopularClubs(@PathVariable int n) {
        return ResponseEntity
                .ok()
                .body(clubService.getTopPopularClubs(n));
    }

    @GetMapping("clubs/events/avg")
    public ResponseEntity<?> getClubEventsAvgInfo(HttpServletRequest request) {
        return ResponseEntity
                .ok()
                .body(clubService.getClubEventsAvgInfo());
    }
}
