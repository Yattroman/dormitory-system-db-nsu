package ru.nsu.yattroman.dormsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.DTO.club.ClubDto;
import ru.nsu.yattroman.dormsys.DTO.metainfo.ClubBoardElement;
import ru.nsu.yattroman.dormsys.DTO.metainfo.ClubEventsAvg;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.clubs.Club;
import ru.nsu.yattroman.dormsys.entity.clubs.ClubManager;
import ru.nsu.yattroman.dormsys.repository.ClubManagerRepository;
import ru.nsu.yattroman.dormsys.repository.ClubRepository;
import ru.nsu.yattroman.dormsys.repository.UserRepository;
import ru.nsu.yattroman.dormsys.service.inerfaces.IClubService;
import ru.nsu.yattroman.dormsys.util.Connect;

import java.util.List;

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

        setClubManagerToClub(club, club.getClubManager().getId());

        return clubRepository.save(club);
    }

    private void manipulateUserAndClubConnection(Long clubId, Long userId, Connect connection){
        var club = clubRepository.findClubById(clubId);
        var user = userRepository.findUserById(userId);

        if(user == null || club == null){
            //TODO: add exception no club or user
            return;
        }

        if(connection.equals(Connect.CONNECT)){
            user.addClub(club);
        } else if(connection.equals(Connect.DISCONNECT)){
            user.removeClub(club);
        }
        userRepository.save(user);
    }

    @Override
    public void subscribeUserToClub(Long clubId, Long userId) {
        manipulateUserAndClubConnection(clubId, userId, Connect.CONNECT);
    }

    @Override
    public void unsubscribeUserFromClub(Long clubId, Long userId) {
        manipulateUserAndClubConnection(clubId, userId, Connect.DISCONNECT);
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

    @Override
    public List<Club> getClubsByUser(Long userId) {
        return clubRepository.findClubsByParticipantsId(userId);
    }

    @Override
    public List<Club> getClubsByClubManager(Long clubManagerId) {
        return clubRepository.findClubsByClubManager_Id(clubManagerId);
    }

    @Override
    public List<ClubBoardElement> getTopPopularClubs(int n) {
        var allClubs = clubRepository.findAllClubsWithParticipantsInfo();
        return allClubs.size() <= n ? allClubs.subList(0, allClubs.size()) : allClubs.subList(0, n);
    }

    @Override
    public List<ClubEventsAvg> getClubsEventsAvgInfo() {
        return clubRepository.findClubsEventsAvg();
    }
}
