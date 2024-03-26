package cbc.webclient.controller;

import cbc.webclient.model.Cat;
import cbc.webclient.model.MacBookPro;
import cbc.webclient.serviceImpl.WebClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private WebClientServiceImpl webClientService;
    @PostMapping("/test")
    public String test(@RequestBody MacBookPro cat){
        logger.info("test call");
        webClientService.addClientDetails(cat);
        return cat.getName();
    }
}
