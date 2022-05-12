package ru.nsu.yattroman.dormsys.service.inerfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.DTO.metainfo.ClubBoardElement;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;

import java.util.List;

public interface IClubService {
    Club addClub(Club club);
    Club showClubDetails(Long clubId);
    Page<Club> showClubsPage(Pageable pageable);
    ClubManager showClubManagerDetails(Long clubManagerId);
    void setClubManagerToClub(Club club, Long clubManagerId);
    void subscribeUserToClub(Long clubId, Long userId);
    void unsubscribeUserFromClub(Long clubId, Long userId);
    List<Club> getClubsByUser(Long userId);
    List<Club> getClubsByClubManager(Long clubManagerId);
    List<ClubBoardElement> getTopPopularClubs(int n);
}
