package ru.nsu.yattroman.dormsys.service.inerfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nsu.yattroman.dormsys.DTO.dormitory.FurnitureDto;
import ru.nsu.yattroman.dormsys.entity.dormitory.Dormitory;
import ru.nsu.yattroman.dormsys.entity.dormitory.Furniture;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.HashMap;
import java.util.List;

public interface IDormitoryService {

    boolean addInhabitantIntoRoom(Long roomId, Long userId, Long daysToLive);
    boolean removeInhabitantFromRoom(Long inhabitantId);
    boolean addFurnitureIntoRoom(Long roomId, Long furnitureId);
    boolean removeFurnitureFromRoom(Long furnitureId);
    Furniture addFurniture(Furniture furniture);
    Room showRoomDetails(Long roomId);
    Inhabitant showInhabitantDetailsByNickname(String nickname);
    Furniture showFurnitureDetailsByInnerCode(String innerCode);
    Page<Room> showAllDormitoryRooms(Dormitory dormitory, Pageable pageable);
    Dormitory loadDormitoryByName(String name);

}
