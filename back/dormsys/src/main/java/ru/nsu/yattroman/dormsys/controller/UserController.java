package ru.nsu.yattroman.dormsys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.mapper.UserMapper;
import ru.nsu.yattroman.dormsys.security.JwtTokenUtil;
import ru.nsu.yattroman.dormsys.service.IUserService;
import ru.nsu.yattroman.dormsys.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/user")
public class UserController {

    private final IUserService userService;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;

    @Autowired
    public UserController(IUserService userService, JwtTokenUtil tokenUtil, UserMapper userMapper) {
        this.userService = userService;
        this.tokenUtil = tokenUtil;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getUserInfo(@RequestHeader(name="Authorization") String token) {
        var userNickname = tokenUtil.getUsernameFromToken(token);
        var user = userService.loadUserByNickname(userNickname);

        return ResponseEntity
                .ok()
                .body(userMapper.toDTO(user));
    }

    @GetMapping("/dormitoryManagerInfo")
    public ResponseEntity<?> getDormitoryManagerInfo(@RequestHeader(name="Authorization") String token) {
        var userNickname = tokenUtil.getUsernameFromToken(token);
        var user = userService.loadUserByNickname(userNickname);

        return ResponseEntity
                .ok()
                .body(userService.getDormitoryManagerInfo(user.getId()));
    }

    }
