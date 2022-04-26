package ru.nsu.yattroman.dormsys.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.roles.Role;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public User toEntity(UserDto dto) {
        return null;
    }

    @Override
    public UserDto toDTO(User entity) {
        UserDto userDto = new UserDto();
        userDto.setNickname(entity.getNickname());
        userDto.setFirstName(entity.getFirstName());
        userDto.setSurname(entity.getSurname());
        userDto.setMiddleName(entity.getMiddleName());
        userDto.setEmail(entity.getEmail());
        userDto.setRoles(entity.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return userDto;
    }
}
