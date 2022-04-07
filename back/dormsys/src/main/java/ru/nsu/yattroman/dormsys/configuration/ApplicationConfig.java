package ru.nsu.yattroman.dormsys.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("ru.nsu.yattroman.dormsys.repository")
public class ApplicationConfig {

}
