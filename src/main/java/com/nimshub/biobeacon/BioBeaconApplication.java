package com.nimshub.biobeacon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BioBeaconApplication {

    Logger logger = LoggerFactory.getLogger(helloController.class);
    public static void main(String[] args) {
        SpringApplication.run(BioBeaconApplication.class, args);
    }

    @RestController
    public class helloController{
        @GetMapping("/")
        public String hello(){
            logger.error("ERROR");
            return "Deployed and Running...";
        }
    }
}
