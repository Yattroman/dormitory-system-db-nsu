package ru.nsu.yattroman.dormsys.service;

public interface IDormitoryService {

    void addInhabitantIntoRoom(String dormitoryName, String roomNumber, Long userId);
    void removeInhabitantFromRoom(String dormitoryName, String roomNumber, Long userId);
    void addFurnitureIntoRoom(String dormitoryName, String roomNumber, Long furnitureId);
    void removeFurnitureFromRoom(String dormitoryName, String roomNumber, Long furnitureId);
    void showAllDormitoryRooms(String dormitoryName);
    void showRoomDetails(String dormitoryName, String roomNumber);

}
