package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.dormitory.DormitoryManager;

@Repository
public interface DormitoryManagerRepository extends JpaRepository<DormitoryManager, Long> {

    DormitoryManager findDormitoryManagerById(Long id);

}
