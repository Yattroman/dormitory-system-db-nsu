package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Dormitory;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomById(Long id);
    Room findRoomByDormitoryAndRoomNumber(Dormitory dormitory, String roomNumber);
    List<Room> findRoomsByDormitory(Dormitory dormitory);

}
