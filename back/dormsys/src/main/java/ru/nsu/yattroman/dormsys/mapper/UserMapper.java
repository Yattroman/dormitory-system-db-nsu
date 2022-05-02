package ru.nsu.yattroman.dormsys.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.roles.Role;

import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = modelMapper.map(dto, User.class);
        // TODO: Roles...

        return user;
    }

    @Override
    public UserDto toDTO(User entity) {

        UserDto userDto = modelMapper.map(entity, UserDto.class);
        userDto.setRoles(entity.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return userDto;
    }
}
