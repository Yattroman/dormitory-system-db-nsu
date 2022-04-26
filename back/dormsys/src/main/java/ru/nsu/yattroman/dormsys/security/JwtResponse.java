package ru.nsu.yattroman.dormsys.security;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
public class JwtResponse implements Serializable {

    private final String jwtToken;
    private final Long userId;
    private final String nickname;
    private final Collection<? extends GrantedAuthority> authorities;
    private final List<String> roles;

    public JwtResponse(String jwtToken, Long userId, String nickname, Collection<? extends GrantedAuthority> authorities, List<String> roles) {
        this.jwtToken = jwtToken;
        this.userId = userId;
        this.nickname = nickname;
        this.authorities = authorities;
        this.roles = roles;
    }
}
