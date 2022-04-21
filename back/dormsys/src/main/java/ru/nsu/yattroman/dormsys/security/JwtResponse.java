package ru.nsu.yattroman.dormsys.security;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class JwtResponse implements Serializable {

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
