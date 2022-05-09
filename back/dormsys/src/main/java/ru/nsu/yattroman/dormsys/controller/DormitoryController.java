
package ru.nsu.yattroman.dormsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.dormitory.FurnitureDto;
import ru.nsu.yattroman.dormsys.controller.request.FurnitureRequest;
import ru.nsu.yattroman.dormsys.controller.request.InhabitantRequest;
import ru.nsu.yattroman.dormsys.mapper.FurnitureMapper;
import ru.nsu.yattroman.dormsys.mapper.InhabitantMapper;
import ru.nsu.yattroman.dormsys.mapper.RoomMapper;
import ru.nsu.yattroman.dormsys.service.inerfaces.IDormitoryService;

import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dormitory/")
public class DormitoryController {

    private final IDormitoryService dormitoryService;
    private final RoomMapper roomMapper;
    private final FurnitureMapper furnitureMapper;
    private final InhabitantMapper inhabitantMapper;

    @Autowired
    public DormitoryController(IDormitoryService dormitoryService, RoomMapper roomMapper,
                               FurnitureMapper furnitureMapper, InhabitantMapper inhabitantMapper) {
        this.dormitoryService = dormitoryService;
        this.roomMapper = roomMapper;
        this.furnitureMapper = furnitureMapper;
        this.inhabitantMapper = inhabitantMapper;
    }

    @GetMapping(value = "furniture/{innerCode}")
    public ResponseEntity<?> getFurnitureDetails(@PathVariable String innerCode){
        return ResponseEntity
                .ok()
                .body(furnitureMapper.toDTO(dormitoryService.showFurnitureDetailsByInnerCode(innerCode)));
    }

    @GetMapping(value = "inhabitant/{nickname}")
    public ResponseEntity<?> getInhabitantDetails(@PathVariable String nickname){

        var inhabitant = dormitoryService.showInhabitantDetailsByNickname(nickname);

        if(inhabitant == null){
            return ResponseEntity
                    .badRequest()
                    .body("No inhabitant with this name found");
        }

        return ResponseEntity
                .ok()
                .body(inhabitantMapper.toDTO(inhabitant));
    }

    @GetMapping(value = "room/{id}")
    public ResponseEntity<?> getRoomDetails(@PathVariable Long id){
        return ResponseEntity
                .ok()
                .body(roomMapper.toDTO(dormitoryService.showRoomDetails(id)));
    }

    @GetMapping(value = "{dormitoryName}/rooms")
    public ResponseEntity<?> showRooms(
            @PathVariable String dormitoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        Pageable pagingWithAscSortByRoomNumber = PageRequest.of(page, size, Sort.by("roomNumber").ascending());
        var dormitory = dormitoryService.loadDormitoryByName(dormitoryName);
        var resultPage = dormitoryService.showDormitoryRoomsPage(dormitory, pagingWithAscSortByRoomNumber);

        var rooms = resultPage.getContent().stream()
                .map(roomMapper::toDTO).collect(Collectors.toList());

        HashMap<String, Object> response = new HashMap<>();
        response.put("rooms", rooms);
        response.put("currentPage", resultPage.getNumber());
        response.put("totalItems", resultPage.getTotalElements());
        response.put("totalPages", resultPage.getTotalPages());

        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping(value = "room/inhabitant")
    public ResponseEntity<?> addInhabitantIntoRoom(@RequestBody InhabitantRequest request){

        if(request == null){
            return ResponseEntity.badRequest().body("Invalid request");
        }

        var result = dormitoryService.addInhabitantIntoRoom(request.getRoomId(), request.getUserId(), request.getDaysToLive());

        // Create exception which means that roomId or userId isn't correct

        return ResponseEntity
                .ok()
                .body("User with id: " + request.getUserId() + " successfully added to room with id: " + request.getRoomId());
    }

    @DeleteMapping(value = "room/inhabitant")
    public ResponseEntity<?> removeInhabitantFromRoom(HttpServletRequest request){
        var inhabitantId = request.getParameter("inhabitantId");

        dormitoryService.removeInhabitantFromRoom(Long.parseLong(inhabitantId));

        return ResponseEntity
                .ok()
                .body("Inhabitant with id: " + inhabitantId + " successfully removed from room.");
    }

    @PostMapping(value = "furniture")
    public ResponseEntity<?> addFurniture(@RequestBody FurnitureDto furnitureDto){
        dormitoryService.addFurniture(furnitureMapper.toEntity(furnitureDto));

        return ResponseEntity
                .ok()
                .body("Furniture successfully added: " + furnitureDto.getInnerCode());
    }

    @PostMapping(value = "room/furniture")
    public ResponseEntity<?> addFurnitureIntoRoom(@RequestBody FurnitureRequest request){

        if(request == null){
            return ResponseEntity.badRequest().body("Invalid request");
        }

        dormitoryService.addFurnitureIntoRoom(request.getRoomId(), request.getFurnitureId());

        return ResponseEntity
                .ok()
                .body("Furniture with id: " + request.getFurnitureId()
                        + " successfully added to room with id: " + request.getRoomId());
    }

    @DeleteMapping(value = "room/furniture")
    public ResponseEntity<?> removeFurnitureFromRoom(HttpServletRequest request){
        var furnitureId = request.getParameter("furnitureId");

        dormitoryService.removeFurnitureFromRoom(Long.parseLong(furnitureId));

        return ResponseEntity
                .ok()
                .body("Furniture with id: " + furnitureId + " successfully removed from room");
    }

}
