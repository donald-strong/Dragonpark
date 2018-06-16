package au.com.rpn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DragonApp {

    public static void main(String[] args) {
        SpringApplication.run(DragonController.class, args);
    }
}
