package me.june.academy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AcademyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademyApplication.class, args);
    }

}
