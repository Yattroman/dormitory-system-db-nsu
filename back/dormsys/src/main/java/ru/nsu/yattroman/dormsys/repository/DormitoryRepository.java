package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Dormitory;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Long> {

    Dormitory findByName(String name);

}
