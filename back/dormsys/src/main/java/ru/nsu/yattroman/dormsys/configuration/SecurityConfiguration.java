package ru.nsu.yattroman.dormsys.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.nsu.yattroman.dormsys.security.SecurityHandler;
import ru.nsu.yattroman.dormsys.service.UserService;

@EnableWebSecurity
public class SecurityConfiguration {

    private final UserService userService;
    private final SecurityHandler securityHandler;

    @Autowired
    public SecurityConfiguration(UserService userService, SecurityHandler securityHandler) {
        this.userService = userService;
        this.securityHandler = securityHandler;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                    .antMatchers("/authenticated/**").authenticated()
                    .antMatchers("/home").permitAll()
                    .and()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .successHandler(securityHandler)
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .and()
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);

        return authenticationProvider;
    }

}
