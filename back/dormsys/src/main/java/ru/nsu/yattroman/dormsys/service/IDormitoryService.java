package ru.nsu.yattroman.dormsys.service;

import ru.nsu.yattroman.dormsys.entity.dormitory.Dormitory;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.List;

public interface IDormitoryService {

    void addInhabitantIntoRoom(String dormitoryName, String roomNumber, Long userId);
    void removeInhabitantFromRoom(String dormitoryName, String roomNumber, Long userId);
    void addFurnitureIntoRoom(String dormitoryName, String roomNumber, Long furnitureId);
    void removeFurnitureFromRoom(String dormitoryName, String roomNumber, Long furnitureId);
    List<Room> showAllDormitoryRooms(Dormitory dormitoryName);
    void showRoomDetails(String dormitoryName, String roomNumber);
    Dormitory loadDormitoryByName(String name);

}
