package com.nimshub.biobeacon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BioBeaconApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioBeaconApplication.class, args);
    }

    @RestController
    public class helloController {
        @GetMapping("/")
        public String hello() {
            return "Up and Running...";
        }
    }
}
