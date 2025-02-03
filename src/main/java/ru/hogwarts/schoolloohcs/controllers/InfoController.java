package ru.hogwarts.schoolloohcs.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.schoolloohcs.services.InfoService;

import static java.lang.String.valueOf;

@RestController
public class InfoController {
    @Autowired
    private InfoService infoService;
    private Logger logger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping(path = "/port")
    public String getPort(){
        logger.info("port: " + infoService.getPort());
        return "port: " + infoService.getPort();

    }

}
