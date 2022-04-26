package ru.nsu.yattroman.dormsys.util;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserRepository userRepository;

    private final DormitoryRepository dormitoryRepository;
    private final RoomRepository roomRepository;

    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(RoleRepository roleRepository, PrivilegeRepository privilegeRepository, UserRepository userRepository,
                           DormitoryRepository dormitoryRepository, RoomRepository roomRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.userRepository = userRepository;
        this.dormitoryRepository = dormitoryRepository;
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        if(alreadySetup)
            return;

        Privilege absoluteWritePrivilege = createPrivilegeIfNotFound("Absolute Write Privilege");
        Privilege absoluteReadPrivilege = createPrivilegeIfNotFound("Absolute Read Privilege");

        createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(absoluteReadPrivilege, absoluteWritePrivilege));
        createRoleIfNotFound("ROLE_USER", null);
        createRoleIfNotFound("ROLE_STUDENT", null);
        createRoleIfNotFound("ROLE_INHABITANT", null);
        createRoleIfNotFound("ROLE_DORMITORY_MANAGER", null);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role dormitoryManagerRole = roleRepository.findByName("ROLE_DORMITORY_MANAGER");
        User user = new User();
        user.setNickname("root");
        user.setFirstName("Sergey");
        user.setSurname("Dovlatov");
        user.setEmail("s.dovlatov@gmail.com");
        user.setRoles(Arrays.asList(dormitoryManagerRole, adminRole));
        user.setPassword(passwordEncoder.encode("test"));
        userRepository.save(user);

        //var dormitory = createDormitoryIfNotFound("тройка");
        //dormitoryRepository.save(dormitory);

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

            // TODO для каждого общежития сделать свою инфу по этажам, комнатам и т.д.
            int roomsPerFloor = 28;
            int floorsNumber = 5;
            for (int i = 0; i < roomsPerFloor*floorsNumber; i++) {
                var roomNumber = new StringBuilder().append(i/floorsNumber + 1).append(i%floorsNumber+1);
                var big = createRoomIfNotFound(dormitory, roomNumber.append("б").toString(), 3);
                var little = createRoomIfNotFound(dormitory, roomNumber.append("м").toString(), 1);
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
