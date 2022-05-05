package ru.nsu.yattroman.dormsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.dormitory.*;
import ru.nsu.yattroman.dormsys.repository.*;
import ru.nsu.yattroman.dormsys.service.inerfaces.IDormitoryService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class DormitoryService implements IDormitoryService {

    private final DormitoryRepository dormitoryRepository;
    private final RoomRepository roomRepository;
    private final FurnitureRepository furnitureRepository;
    private final InhabitantRepository inhabitantRepository;

    private final UserRepository userRepository;

    @Autowired
    public DormitoryService(DormitoryRepository dormitoryRepository, FurnitureRepository furnitureRepository,
                            RoomRepository roomRepository, UserRepository userRepository,
                            InhabitantRepository inhabitantRepository) {
        this.dormitoryRepository = dormitoryRepository;
        this.furnitureRepository = furnitureRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.inhabitantRepository = inhabitantRepository;
    }

    @Override
    public boolean addInhabitantIntoRoom(Long roomId, Long userId, Long daysToLive) {
        var user = userRepository.findUserById(userId);
        var room = roomRepository.findRoomById(roomId);

        if(user == null || room == null){
            return false;
        }

        // TODO: check if room isn't necessary

        var inhabitant = createInhabitantInfo(user, room, daysToLive);
        user.setInhabitant(inhabitant);

        roomRepository.save(room);
        userRepository.save(user);

        return true;
    }

    private Inhabitant createInhabitantInfo(User user, Room room, Long daysToLive){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        var startTime = LocalDate.now();
        var endTime = startTime.plusDays(daysToLive);
        var contract = new Contract(Date.from(startTime.atStartOfDay(defaultZoneId).toInstant()),
                                    Date.from(endTime.atStartOfDay(defaultZoneId).toInstant()));

        var inhabitantInfo = new Inhabitant();
        inhabitantInfo.setContract(contract);
        inhabitantInfo.setRoom(room);
        inhabitantInfo.setUser(user);
        // TODO: add achievements and offenses

        // TODO: add role ROLE_INHABITANT

        contract.setInhabitant(inhabitantInfo);
        user.setInhabitant(inhabitantInfo);

        inhabitantRepository.save(inhabitantInfo);

        return inhabitantInfo;
    }

    @Override
    public boolean removeInhabitantFromRoom(Long inhabitantId) {
        var inhabitant = inhabitantRepository.findInhabitantById(inhabitantId);
        var user = userRepository.findUserById(inhabitantId);

        if(inhabitant == null){
            return false;
        }

        inhabitant.setRoom(null);
        inhabitantRepository.save(inhabitant);

        user.setInhabitant(null);
        inhabitantRepository.deleteById(inhabitantId);

        return true;
    }

    @Override
    public Furniture addFurniture(Furniture furniture) {
        var existingFurniture = furnitureRepository.findFurnitureByInnerCode(furniture.getInnerCode());

        if(existingFurniture != null){
            // send exception furniture is already exists
            return null;
        }

        return furnitureRepository.save(furniture);
    }

    @Override
    public boolean addFurnitureIntoRoom(Long roomId, Long furnitureId) {
        var furniture = furnitureRepository.findFurnitureById(furnitureId);
        var room = roomRepository.findRoomById(roomId);

        if(furniture == null || room == null){
            return false;
        }

        furniture.setRoom(room);
        furnitureRepository.save(furniture);

        return true;
    }

    @Override
    public boolean removeFurnitureFromRoom(Long furnitureId) {
        var furniture = furnitureRepository.findFurnitureById(furnitureId);

        if(furniture == null){
            return false;
        }

        furniture.setRoom(null);
        furnitureRepository.save(furniture);

        return true;
    }

    @Override
    public Page<Room> showDormitoryRoomsPage(Dormitory dormitory, Pageable pageable) {
        return roomRepository.findRoomsByDormitory(dormitory, pageable);
    }

    @Override
    public Room showRoomDetails(Long roomId) {
        return roomRepository.findRoomById(roomId);
    }

    @Override
    public Inhabitant showInhabitantDetailsByNickname(String nickname) {
        return inhabitantRepository.findInhabitantByUserNickname(nickname);
    }

    @Override
    public Furniture showFurnitureDetailsByInnerCode(String innerCode) {
        return furnitureRepository.findFurnitureByInnerCode(innerCode);
    }

    @Override
    public Dormitory loadDormitoryByName(String name) {
        return dormitoryRepository.findByName(name);
    }

}
