package ru.nsu.yattroman.dormsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.DTO.metainfo.ClubBoardElement;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    Club findClubByUniqueName(String uniqueName);
    Club findClubById(Long id);
    List<Club> findClubsByParticipantsId(Long participantId);
    List<Club> findClubsByClubManager_Id(Long clubManagerId);
    @Query(value = "select new ru.nsu.yattroman.dormsys.DTO.metainfo.ClubBoardElement(" +
            "c.id, c.name, c.uniqueName, count(cp), concat(cmu.surname, ' ', cmu.firstName)) " +
            "from Club c " +
            "left outer join c.participants cp " +
            "left outer join c.clubManager cm " +
            "left outer join cm.user cmu " +
            "group by c, cmu, cm order by count(cp) desc ")
    List<ClubBoardElement> findAllClubsWithParticipantsInfo();
}
