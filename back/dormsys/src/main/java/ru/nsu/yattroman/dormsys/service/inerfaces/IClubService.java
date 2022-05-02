package ru.nsu.yattroman.dormsys.service.inerfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;

public interface IClubService {

    Club addClub(Club club);
    Club showClubDetails(Long clubId);
    Page<Club> showAllClubs(Pageable pageable);
    ClubManager showClubManagerDetails(Long clubManagerId);
    void subscribeUserToClub(Long clubId, Long userId);
    void unsubscribeUserFromClub(Long clubId, Long userId);

}
