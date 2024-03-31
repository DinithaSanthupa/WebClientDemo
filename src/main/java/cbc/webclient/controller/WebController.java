package cbc.webclient.controller;

import cbc.webclient.model.*;
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

    @PostMapping("/feedback")
    public FeedBackResponse getFeedBack(@RequestBody FeedbackRequest feedbackRequest){
        logger.info("Action: {}, info: {}", "feedback request", feedbackRequest);
        return webClientService.getFeedBack(feedbackRequest);
    }

    @PostMapping("/chat")
    public ChatRootResponse getChatHiResponse(@RequestBody ChatMessage chatMessage){
        logger.info("Action: {}, info: {}", "HiMessage request", chatMessage);
        return webClientService.getChatResponse(chatMessage.getQuery());
    }
}
