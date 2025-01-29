package ru.hogwarts.schoolloohcs.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.valueOf;

@RestController
public class InfoController {
    private Logger logger = LoggerFactory.getLogger(InfoController.class);
    @Value("${server.port}")
    private int port;

    @GetMapping(path = "/port")
    public String getPort(){
        logger.info("port: " + port);
        return "port: " + port;

    }

}
