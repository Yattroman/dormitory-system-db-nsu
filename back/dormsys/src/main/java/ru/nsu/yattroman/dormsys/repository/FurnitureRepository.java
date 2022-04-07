package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Furniture;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.List;

public interface FurnitureRepository extends JpaRepository<Furniture, Long> {

    Furniture getFurnitureById(Long id);
    List<Furniture> getFurnituresByRoom(Room room);

}
