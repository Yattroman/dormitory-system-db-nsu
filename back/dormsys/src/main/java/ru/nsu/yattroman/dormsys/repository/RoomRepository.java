package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Dormitory;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findRoomById(Long id);
    Room findRoomByDormitoryAndRoomNumber(Dormitory dormitory, String roomNumber);
    Page<Room> findRoomsByDormitory(Dormitory dormitory, Pageable pageable);

}
