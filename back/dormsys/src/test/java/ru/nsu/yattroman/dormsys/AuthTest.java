package ru.nsu.yattroman.dormsys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.nsu.yattroman.dormsys.DTO.UserDto;
import ru.nsu.yattroman.dormsys.entity.User;
import ru.nsu.yattroman.dormsys.service.inerfaces.IUserService;
import ru.nsu.yattroman.dormsys.util.Gender;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthTest {

    private IUserService userService;
    private PasswordEncoder passwordEncoder;
    private Validator validator;

    @Autowired
    public AuthTest(IUserService userService, PasswordEncoder passwordEncoder, Validator validator) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Test
    void whenRegistered_thenFindByNicknameAndCheckData() {
        userService.registerNewUserAccount(
                new User("admin", "password", "admin@gmail.com",
                        "Anton", "Mikhailovich", "Smith", null, Gender.MALE)
        );
        assertThat(userService.loadUserByNickname("admin")).isNotNull();
        var user = userService.loadUserByNickname("admin");
        assertThat(user.getNickname()).isEqualTo("admin");
        assertThat(passwordEncoder.matches("password", user.getPassword())).isTrue();
        assertThat(user.getEmail()).isEqualTo("admin@gmail.com");
        assertThat(user.getFirstName()).isEqualTo("Anton");
        assertThat(user.getSurname()).isEqualTo("Smith");
        assertThat(user.getMiddleName()).isEqualTo("Mikhailovich");
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    void whenRegisteredWithEmptyRequiredField_thenCheck() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userService.registerNewUserAccount(new User("test", "password", "test@gmail.com",
                            null, "Mikhailovich", "Smith", null, Gender.MALE)
            );
        });
    }

    @Test
    void whenRegisteredWithInvalidRequiredField_thenCheck() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userService.registerNewUserAccount(new User("testtesttesttesttesttesttesttesttesttesttesttest"
                    , "password", "test@gmail.com",null, "Mikhailovich",
                    "Smith", null, Gender.MALE)
            );
        });
    }

    @Test
    void checkUserDtoWithInvalidData(){
        UserDto userDto = new UserDto();
        userDto.setFirstName("Roman");
        userDto.setSurname("Yatmanov");
        userDto.setPassword("password");
        userDto.setNickname("nicknamenicknamenicknamenickname");
        userDto.setEmail("emailgmail.com");
        BindingResult bindingResult = new BeanPropertyBindingResult(userDto, "userDto");
        validator.validate(userDto, bindingResult);
        Assertions.assertTrue(bindingResult.hasFieldErrors("email"));
        Assertions.assertFalse(bindingResult.hasFieldErrors("firstName"));
        Assertions.assertFalse(bindingResult.hasFieldErrors("surname"));
        Assertions.assertFalse(bindingResult.hasFieldErrors("password"));
        Assertions.assertTrue(bindingResult.hasFieldErrors("nickname"));
    }
}

