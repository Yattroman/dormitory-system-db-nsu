package ru.nsu.yattroman.dormsys.util;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.dormitory.Dormitory;
import ru.nsu.yattroman.dormsys.entity.dormitory.Room;
import ru.nsu.yattroman.dormsys.entity.roles.Privilege;
import ru.nsu.yattroman.dormsys.entity.roles.Role;
import ru.nsu.yattroman.dormsys.repository.*;

import java.util.*;

@Component
@Slf4j
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;
    private final DormitoryRepository dormitoryRepository;
    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(RoleRepository roleRepository, PrivilegeRepository privilegeRepository, UserRepository userRepository,
                           EventRepository eventRepository, ClubRepository clubRepository, DormitoryRepository dormitoryRepository,
                           RoomRepository roomRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
        this.dormitoryRepository = dormitoryRepository;
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private User createRootUser(){
        Role adminRole = roleRepository.findByName(InnerRole.ROLE_ADMIN.name());
        Role dormitoryManagerRole = roleRepository.findByName(InnerRole.ROLE_DORMITORY_MANAGER.name());
        User user = new User();
        user.setNickname("root");
        user.setFirstName("Sergey");
        user.setSurname("Dovlatov");
        user.setEmail("s.dovlatov@gmail.com");
        user.setRoles(Arrays.asList(dormitoryManagerRole, adminRole));
        user.setPassword(passwordEncoder.encode("test"));

        return user;
    }

    private User getNotRepeatedUser(HashMap<Long, Boolean> usersMap){
        long userIDStart = 291;
        long userIDEnd = 486;
        long userId = userIDStart + RandomHelper.createRandomLongBetween(0, userIDEnd-userIDStart);
        while(usersMap.get(userId) != null){
            userId = userIDStart + RandomHelper.createRandomLongBetween(0, userIDEnd-userIDStart);
        }
        usersMap.put(userId, true);

        return userRepository.findUserById(userId);
    }

    @Transactional
    private void enrollUsersToEvents(){
        int maxEventParticipants = 20;
        var events = eventRepository.findAll();
        for (var event : events) {
            var participantsNumber = RandomHelper.createRandomIntBetween(0, maxEventParticipants);
            for (long j = 0; j < participantsNumber; j++) {
                HashMap<Long, Boolean> usersMap = new HashMap<>();
                var user = getNotRepeatedUser(usersMap);
                user.addEvent(event);
                userRepository.save(user);
            }
        }
    }

    @Transactional
    private void subscribeUsersToClubs(){
        int maxClubParticipants = 40;
        var clubs = clubRepository.findAll();
        for (var club : clubs) {
            var participantsNumber = RandomHelper.createRandomIntBetween(0, maxClubParticipants);
            for (long j = 0; j < participantsNumber; j++) {
                HashMap<Long, Boolean> usersMap = new HashMap<>();
                var user = getNotRepeatedUser(usersMap);
                user.addClub(club);
                userRepository.save(user);
            }
        }
    }

    public User createUser(String firstName, String surname, Gender gender){
        var nickname = firstName.toLowerCase(Locale.ROOT) + surname.toLowerCase(Locale.ROOT);
        return new User(
                nickname,
                passwordEncoder.encode(nickname),
                nickname + "@gmail.com",
                firstName,
                surname,
                RandomHelper.createRandomDate(2000, 2003),
                gender
        );
    }

    @Transactional
    public void createUsers(){
        for (int i = 0; i < UserGeneratorUtil.surnames.length; i++) {
            for (int j = 0; j < UserGeneratorUtil.manNames.length; j++) {
                userRepository.save(createUser(UserGeneratorUtil.manNames[j], UserGeneratorUtil.surnames[i], Gender.MALE));
            }
            for (int j = 0; j < UserGeneratorUtil.womanNames.length; j++) {
                userRepository.save(createUser(UserGeneratorUtil.womanNames[j], UserGeneratorUtil.surnames[i], Gender.FEMALE));
            }
        }
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        if(alreadySetup)
            return;

        Privilege absoluteWritePrivilege = createPrivilegeIfNotFound("Absolute Write Privilege");
        Privilege absoluteReadPrivilege = createPrivilegeIfNotFound("Absolute Read Privilege");

        createRoleIfNotFound(InnerRole.ROLE_ADMIN.name(), Arrays.asList(absoluteReadPrivilege, absoluteWritePrivilege));
        createRoleIfNotFound(InnerRole.ROLE_USER.name(), null);
        createRoleIfNotFound(InnerRole.ROLE_STUDENT.name(), null);
        createRoleIfNotFound(InnerRole.ROLE_INHABITANT.name(), null);
        createRoleIfNotFound(InnerRole.ROLE_DORMITORY_MANAGER.name(), null);
        createRoleIfNotFound(InnerRole.ROLE_CLUB_MANAGER.name(), null);

        userRepository.save(createRootUser());

        createDormitoryIfNotFound("third");
        createUsers();
        subscribeUsersToClubs();
        enrollUsersToEvents();

        alreadySetup = true;
    }

    @Transactional
    private Room createRoomIfNotFound(Dormitory dormitory, String roomNumber, int bedsNumber){
        Room room = roomRepository.findRoomByDormitoryAndRoomNumber(dormitory, roomNumber);
        if(room == null){
            room = new Room(roomNumber, bedsNumber);
            room.setDormitory(dormitory);
        }

        return room;
    }

    @Transactional
    private Dormitory createDormitoryIfNotFound(String name){
        Dormitory dormitory = dormitoryRepository.findByName(name);
        if(dormitory == null){
            dormitory = new Dormitory(name);
            dormitory.setRooms(new HashSet<>());
            dormitoryRepository.save(dormitory);

            // TODO для каждого общежития сделать свою инфу по этажам, комнатам и т.д.
            //  [т.е. по категории общежития заполнить количество комнат на этаже, количество этажей]
            int roomsPerFloor = 28;
            int floorsNumber = 5;
            for (int i = 0; i < roomsPerFloor*floorsNumber; i++) {
                var roomNumber = new StringBuilder().append(i/floorsNumber + 1).append(i%floorsNumber + 1);
                var big = createRoomIfNotFound(dormitory, roomNumber.append("б").toString(), 3);
                var little = createRoomIfNotFound(dormitory, roomNumber.deleteCharAt(roomNumber.length()-1).append("м").toString(), 1);
                dormitory.addRoom(big);
                dormitory.addRoom(little);
            }

            dormitoryRepository.save(dormitory);
        }

        return dormitory;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
