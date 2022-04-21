package ru.nsu.yattroman.dormsys.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.exceptions.UserAlreadyExistException;
import ru.nsu.yattroman.dormsys.security.JwtRequest;
import ru.nsu.yattroman.dormsys.security.JwtTokenUtil;
import ru.nsu.yattroman.dormsys.service.UserService;

import java.security.Security;

@RestController
@CrossOrigin(origins = "*") // TODO разобраться с CORS, сделать его на глобальном уровне
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil tokenUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil tokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerNewUserAccount(@RequestBody final UserDto userDto, HttpServletRequest request, Error errors){

        try {
            var user = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            return new ResponseEntity<String>("User is already registered! " + uaeEx.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("User successfully created! Thank u!", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest jwtRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getNickname(), jwtRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Dangerous - what authentication do?
        User user =  (User) authentication.getPrincipal();

        String jwt = tokenUtil.generateToken(user);


        // To Do - user to userDTO mapper
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION,jwt)
                .body(user);
    }

}
