package ru.nsu.yattroman.dormsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.dormitory.Contract;
import ru.nsu.yattroman.dormsys.entity.dormitory.Inhabitant;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;
import ru.nsu.yattroman.dormsys.repository.DormitoryRepository;
import ru.nsu.yattroman.dormsys.repository.FurnitureRepository;
import ru.nsu.yattroman.dormsys.repository.RoomRepository;
import ru.nsu.yattroman.dormsys.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class DormitoryService implements IDormitoryService {

    private final DormitoryRepository dormitoryRepository;
    private final RoomRepository roomRepository;
    private final FurnitureRepository furnitureRepository;

    private final UserRepository userRepository;

    @Autowired
    public DormitoryService(DormitoryRepository dormitoryRepository, FurnitureRepository furnitureRepository,
                            RoomRepository roomRepository, UserRepository userRepository) {
        this.dormitoryRepository = dormitoryRepository;
        this.furnitureRepository = furnitureRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addInhabitantIntoRoom(String dormitoryName, String roomNumber, Long userId) {
        var user = userRepository.findUserById(userId);
        var dormitory = dormitoryRepository.findByName(dormitoryName);
        var room = roomRepository.findRoomByDormitoryAndRoomNumber(dormitory, roomNumber);

        user.setInhabitant(createInhabitantInfo(user, room, 100));
    }

    private Inhabitant createInhabitantInfo(User user, Room room, int daysToLive){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        var startTime = LocalDate.now();
        var endTime = startTime.plusDays(daysToLive);
        var contract = new Contract(Date.from(startTime.atStartOfDay(defaultZoneId).toInstant()),
                                    Date.from(endTime.atStartOfDay(defaultZoneId).toInstant()));

        var inhabitantInfo = new Inhabitant();
        inhabitantInfo.setContract(contract);
        inhabitantInfo.setRoom(room);
        inhabitantInfo.setUser(user);
        // TODO add achievements and offenses

        contract.setInhabitant(inhabitantInfo);
        user.setInhabitant(inhabitantInfo);

        return inhabitantInfo;
    }

    @Override
    public void removeInhabitantFromRoom(String dormitoryName, String roomNumber, Long userId) {

    }

    @Override
    public void addFurnitureIntoRoom(String dormitoryName, String roomNumber, Long furnitureId) {

    }

    @Override
    public void removeFurnitureFromRoom(String dormitoryName, String roomNumber, Long furnitureId) {

    }

    @Override
    public void showAllDormitoryRooms(String dormitoryName) {

    }

    @Override
    public void showRoomDetails(String dormitoryName, String roomNumber) {

    }
}
