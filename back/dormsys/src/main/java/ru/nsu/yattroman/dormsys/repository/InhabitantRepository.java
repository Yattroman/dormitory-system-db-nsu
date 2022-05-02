package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;

@Repository
public interface InhabitantRepository extends JpaRepository<Inhabitant, Long> {

    Inhabitant findInhabitantByUserNickname(String nickname);
    Inhabitant findInhabitantById(Long id);

}
