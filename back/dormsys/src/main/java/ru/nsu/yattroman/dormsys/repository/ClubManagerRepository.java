package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;

@Repository
public interface ClubManagerRepository extends JpaRepository<ClubManager, Long> {

    ClubManager findClubManagerById(Long id);

}
