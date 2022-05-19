package ru.nsu.yattroman.dormsys;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.nsu.yattroman.dormsys.service.inerfaces.IUserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthTest {

    private IUserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthTest(IUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void whenRegistered_thenFindByNicknameAndCheckData() {
    }
}

