package au.com.dragon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DragonApp {

    public static void main(String[] args) {
        SpringApplication.run(DragonController.class, args);
    }
}
