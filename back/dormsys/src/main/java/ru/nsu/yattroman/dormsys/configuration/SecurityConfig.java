package ru.nsu.yattroman.dormsys.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.nsu.yattroman.dormsys.security.JwtAuthenticationEntryPoint;
import ru.nsu.yattroman.dormsys.security.JwtTokenFilter;
import ru.nsu.yattroman.dormsys.security.SecurityHandler;
import ru.nsu.yattroman.dormsys.service.JwtUserDetailsService;

@EnableWebSecurity
public class SecurityConfig {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final SecurityHandler securityHandler;
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private AuthenticationManager authenticationManager;

    @Autowired
    public SecurityConfig(JwtUserDetailsService jwtUserDetailsService, SecurityHandler securityHandler,
                          JwtTokenFilter jwtTokenFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtUserDetailsService = jwtUserDetailsService;
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

        authenticationManager = http.getSharedObject(AuthenticationManager.class);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Пока так, я пока я не разобрался с auth manager
    @Bean
    public AuthenticationManager authenticationManager(){
        return this.authenticationManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(jwtUserDetailsService);

        return authenticationProvider;
    }


}
