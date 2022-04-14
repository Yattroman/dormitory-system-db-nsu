package ru.nsu.yattroman.dormsys.service;

import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;

public interface IUserService {

    User registerNewUserAccount(UserDto userDto);

}
