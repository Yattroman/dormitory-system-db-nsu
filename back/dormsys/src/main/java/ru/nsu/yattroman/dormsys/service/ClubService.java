package ru.nsu.yattroman.dormsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;
import ru.nsu.yattroman.dormsys.repository.ClubManagerRepository;
import ru.nsu.yattroman.dormsys.repository.ClubRepository;
import ru.nsu.yattroman.dormsys.repository.UserRepository;
import ru.nsu.yattroman.dormsys.service.inerfaces.IClubService;

@Service
public class ClubService implements IClubService {

    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubManagerRepository clubManagerRepository;

    @Autowired
    public ClubService(ClubRepository clubRepository, UserRepository userRepository, ClubManagerRepository clubManagerRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
        this.clubManagerRepository = clubManagerRepository;
    }

    @Override
    public Club addClub(Club club) {

        var existingClub = clubRepository.findClubByUniqueName(club.getUniqueName());

        if(existingClub != null){
            // send exception furniture is already exists
           return null;
        }

        return clubRepository.save(club);
    }

    @Override
    public void subscribeUserToClub(Long clubId, Long userId) {

    }

    @Override
    public void unsubscribeUserFromClub(Long clubId, Long userId) {

    }

    @Override
    public void setClubManagerToClub(Club club, Long clubManagerId) {
        var clubManager = clubManagerRepository.findClubManagerById(clubManagerId);
        var user = userRepository.findUserById(clubManagerId);

        if(clubManager == null){
            clubManager = createClubManagerInfo(user);
        }

        club.setClubManager(clubManager);
        clubRepository.save(club);
    }

    private ClubManager createClubManagerInfo(User user){
        var clubManagerInfo = new ClubManager();
        clubManagerInfo.setUser(user);
        user.setClubManager(clubManagerInfo);

        // TODO: add role ROLE_CLUB_MANAGER

        clubManagerRepository.save(clubManagerInfo);
        userRepository.save(user);

        return clubManagerInfo;
    }

    @Override
    public Page<Club> showClubsPage(Pageable pageable) {
        return clubRepository.findAll(pageable);
    }

    @Override
    public Club showClubDetails(Long clubId) {
        return clubRepository.findClubById(clubId);
    }

    @Override
    public ClubManager showClubManagerDetails(Long clubManagerId) {
        return clubManagerRepository.findClubManagerById(clubManagerId);
    }
}
