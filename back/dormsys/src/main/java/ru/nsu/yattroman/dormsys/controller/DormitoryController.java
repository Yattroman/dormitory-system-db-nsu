package ru.nsu.yattroman.dormsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.nsu.yattroman.dormsys.service.DormitoryService;

@RestController
@RequestMapping("/dormitory/{dormitoryName}")
public class DormitoryController {

    private final DormitoryService dormitoryService;

    @Autowired
    public DormitoryController(DormitoryService dormitoryService) {
        this.dormitoryService = dormitoryService;
    }

    @PostMapping(value = "/room/inhabitant")
    void addInhabitantIntoRoom(HttpServletRequest request,
                               UriComponentsBuilder uriComponentsBuilder,
                               @PathVariable String dormitoryName){
        var roomNumber = request.getParameter("room_number");
        var userId = request.getParameter("user_id");

//        dormitoryService.addInhabitantIntoRoom(dormitoryName, roomNumber, Long.parseLong(userId));
    }

    @DeleteMapping(value = "/room/inhabitant")
    void removeInhabitantFromRoom(HttpServletRequest request,
                                  UriComponentsBuilder uriComponentsBuilder,
                                  @PathVariable String dormitoryName){
        var roomNumber = request.getParameter("room_number");
        var userId = request.getParameter("user_id");

//        dormitoryService.removeInhabitantFromRoom(dormitoryName, roomNumber, Long.parseLong(userId));
    }

    @PostMapping(value = "/room/furniture")
    void addFurnitureIntoRoom(HttpServletRequest request,
                               UriComponentsBuilder uriComponentsBuilder,
                               @PathVariable String dormitoryName){
        var roomNumber = request.getParameter("room_number");
        var furnitureId = request.getParameter("furniture_id");

//        dormitoryService.addFurnitureIntoRoom(dormitoryName, roomNumber, Long.parseLong(furnitureId));
    }

    @DeleteMapping(value = "/room/furniture")
    void removeFurnitureFromRoom(HttpServletRequest request,
                                  UriComponentsBuilder uriComponentsBuilder,
                                  @PathVariable String dormitoryName){
        var roomNumber = request.getParameter("room_number");
        var furnitureId = request.getParameter("furniture_id");

//        dormitoryService.removeFurnitureFromRoom(dormitoryName, roomNumber, Long.parseLong(furnitureId));
    }

    @GetMapping(value = "/rooms")
    void showRooms(@PathVariable String dormitoryName){
        dormitoryService.showAllDormitoryRooms(dormitoryName);
    }

}
