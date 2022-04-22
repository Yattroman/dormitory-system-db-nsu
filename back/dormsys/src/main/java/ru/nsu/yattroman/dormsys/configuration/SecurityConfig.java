package ru.nsu.yattroman.dormsys.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.nsu.yattroman.dormsys.security.JwtAuthenticationEntryPoint;
import ru.nsu.yattroman.dormsys.security.JwtTokenFilter;
import ru.nsu.yattroman.dormsys.security.SecurityHandler;
import ru.nsu.yattroman.dormsys.service.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final SecurityHandler securityHandler;
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, SecurityHandler securityHandler,
                          JwtTokenFilter jwtTokenFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.securityHandler = securityHandler;
        this.jwtTokenFilter = jwtTokenFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http = http
                .csrf().disable()
                .cors()
                .and();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .authorizeRequests()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/home").permitAll()
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and();

        http = http
                .formLogin()
                    .loginProcessingUrl("/login")
                    .successHandler(securityHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                .and();

        http = http.addFilterBefore(
                jwtTokenFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);

        return authenticationProvider;
    }


}
