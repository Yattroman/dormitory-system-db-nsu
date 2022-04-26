package ru.nsu.yattroman.dormsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.dormitory.DormitoryManager;
import ru.nsu.yattroman.dormsys.exceptions.UserAlreadyExistException;
import ru.nsu.yattroman.dormsys.repository.DormitoryManagerRepository;
import ru.nsu.yattroman.dormsys.repository.RoleRepository;
import ru.nsu.yattroman.dormsys.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements IUserService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final DormitoryManagerRepository dormitoryManagerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, DormitoryManagerRepository dormitoryManagerRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dormitoryManagerRepository = dormitoryManagerRepository;
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if(nicknameExist(userDto.getNickname())){
            throw new UserAlreadyExistException("User with specified nickname is already exist!");
        }

        if(emailExist(userDto.getEmail())){
            throw new UserAlreadyExistException("User with specified email is already exist!");
        }

        User user = new User();

        user.setNickname(userDto.getNickname());
        user.setFirstName(userDto.getFirstName());
        user.setSurname(userDto.getSurname());
        user.setMiddleName(userDto.getMiddleName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        var role = roleRepository.findByName("ROLE_USER");

        user.setRoles(Collections.singletonList(role));

        System.out.println(user);

        return userRepository.save(user);
    }

    @Override
    public User loadUserByNickname(String nickname) {
        return userRepository.findUserByNickname(nickname);
    }

    @Override
    public DormitoryManager getDormitoryManagerInfo(Long userId) {
        return dormitoryManagerRepository.findDormitoryManagerById(userId);
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    private boolean nicknameExist(String nickname) {
        return userRepository.findUserByNickname(nickname) != null;
    }

}
