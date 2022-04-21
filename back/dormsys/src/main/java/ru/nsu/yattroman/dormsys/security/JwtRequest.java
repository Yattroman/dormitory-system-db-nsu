package ru.nsu.yattroman.dormsys.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    private String nickname;
    private String password;

}
