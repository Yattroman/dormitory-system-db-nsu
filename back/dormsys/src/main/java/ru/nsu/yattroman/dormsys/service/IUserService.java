package ru.nsu.yattroman.dormsys.service;

import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.dormitory.DormitoryManager;

public interface IUserService {

    User registerNewUserAccount(UserDto userDto);
    User loadUserByNickname(String nickname);

    DormitoryManager getDormitoryManagerInfo(Long id);

}
