package vn.edu.iuh.fit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // Dùng để bật tính năng caching trong Spring Boot

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
