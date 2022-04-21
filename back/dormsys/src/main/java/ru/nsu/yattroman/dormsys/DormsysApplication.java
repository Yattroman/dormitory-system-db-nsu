package ru.nsu.yattroman.dormsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.nsu.yattroman.dormsys.configuration.ApplicationConfig;
import ru.nsu.yattroman.dormsys.configuration.SecurityConfig;

@SpringBootApplication
@Import({ApplicationConfig.class, SecurityConfig.class})
public class DormsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(DormsysApplication.class, args);
	}

}
