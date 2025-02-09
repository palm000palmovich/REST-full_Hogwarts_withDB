package ru.hogwarts.schoolloohcs.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
public class InfoServiceProduction implements InfoService {
    private Logger logger = LoggerFactory.getLogger(InfoServiceProduction.class);
    private final int port = 8081;
    @Override
    public String getPort(){
        logger.info("Port: " + port);
        return "Port: " + port;
    }
}
