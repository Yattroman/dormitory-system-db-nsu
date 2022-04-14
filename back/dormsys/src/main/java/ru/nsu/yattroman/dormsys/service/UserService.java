package ru.nsu.yattroman.dormsys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.exceptions.UserAlreadyExistException;
import ru.nsu.yattroman.dormsys.repository.RoleRepository;
import ru.nsu.yattroman.dormsys.repository.UserRepository;

import java.util.Collections;

@Service
public class UserService implements IUserService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if(emailExist(userDto.getEmail()) || nicknameExist(userDto.getNickname())){
            throw new UserAlreadyExistException("User with specified email or nickname is already exist!");
        }

        User user = new User();
        user.setNickname(userDto.getNickname());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        var role = roleRepository.findByName("ROLE_USER");

        user.setRoles(Collections.singletonList(role));

        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    private boolean nicknameExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

}
