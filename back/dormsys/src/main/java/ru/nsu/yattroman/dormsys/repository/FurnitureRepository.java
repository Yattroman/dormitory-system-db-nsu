package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Furniture;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;

import java.util.List;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {

    Furniture findFurnitureById(Long id);
    Furniture findFurnitureByInnerCode(String innerCode);

}
