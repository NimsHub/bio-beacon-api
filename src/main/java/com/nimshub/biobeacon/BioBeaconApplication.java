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

    @Value("${server.port}")
    String port;

    @RestController
    public class helloController {
        @GetMapping("/")
        public String hello() {
            return String.format("Up and Running on Port %s ...", port);
        }
    }
}
