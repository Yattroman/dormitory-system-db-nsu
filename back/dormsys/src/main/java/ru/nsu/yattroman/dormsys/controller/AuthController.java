package ru.nsu.yattroman.dormsys.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.entity.roles.Role;
import ru.nsu.yattroman.dormsys.exceptions.UserAlreadyExistException;
import ru.nsu.yattroman.dormsys.mapper.UserMapper;
import ru.nsu.yattroman.dormsys.security.JwtRequest;
import ru.nsu.yattroman.dormsys.security.JwtResponse;
import ru.nsu.yattroman.dormsys.security.JwtTokenUtil;
import ru.nsu.yattroman.dormsys.service.inerfaces.IUserService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*") // TODO разобраться с CORS, сделать его на глобальном уровне
@RequestMapping("/api/auth")
public class AuthController {

    private final IUserService userService;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public AuthController(IUserService userService, UserMapper userMapper, JwtTokenUtil jwtTokenUtil, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerNewUserAccount(@Valid @RequestBody final UserDto userDto){

        try {
            var user = userMapper.toEntity(userDto);
            userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            return new ResponseEntity<>("User is already registered! " + uaeEx.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User successfully created!", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest jwtRequest){

        var authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getNickname(), jwtRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails =  (UserDetails) authentication.getPrincipal();
        String jwt = jwtTokenUtil.generateToken(userDetails);
        User user = userService.loadUserByNickname(userDetails.getUsername());
        var userRoles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(new JwtResponse(
                        jwt, user.getId(), user.getNickname(), userDetails.getAuthorities(), userRoles
                ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
