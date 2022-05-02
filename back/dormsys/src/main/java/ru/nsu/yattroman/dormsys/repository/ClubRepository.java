package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Club findClubByUniqueName(String uniqueName);
    Club findClubById(Long id);

}
