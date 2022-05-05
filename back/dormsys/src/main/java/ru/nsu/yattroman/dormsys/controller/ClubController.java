package ru.nsu.yattroman.dormsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.mapper.ClubMapper;
import ru.nsu.yattroman.dormsys.service.ClubService;
import ru.nsu.yattroman.dormsys.service.inerfaces.IClubService;

import java.util.HashMap;
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
    public ResponseEntity<?> addClub(@RequestBody ClubDto clubDto){

        var club = clubMapper.toEntity(clubDto);

        //clubService.addClub(club);
        //clubService.setClubManagerToClub(club, clubDto.getClubManager().getId());

        return ResponseEntity
                .ok()
                .body("Club successfully added.");
    }

    @GetMapping("club/{clubId}")
    public ResponseEntity<?> showClubDetails(@PathVariable Long clubId){

        var club = clubService.showClubDetails(clubId);

        if(club == null){
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
                                       @RequestParam(defaultValue = "10") int size){

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

}
