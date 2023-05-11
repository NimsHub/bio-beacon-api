package com.nimshub.biobeacon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BioBeaconApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioBeaconApplication.class, args);
    }

    @Value("test-key")
    String key;

    @RestController
    public class helloController {
        @GetMapping("/")
        public String hello() {
            return "Up and Running... "+key;
        }
    }
}
