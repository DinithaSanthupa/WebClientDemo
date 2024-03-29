package cbc.webclient.serviceImpl;

import cbc.webclient.controller.WebController;
import cbc.webclient.model.*;
import cbc.webclient.service.WebClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
public class WebClientServiceImpl implements WebClientService {

    Logger logger = LoggerFactory.getLogger(WebClientServiceImpl.class);
    private WebClient.Builder getWebClientBuilder;
    @Value("${timeout}")
    private int timeout;

    public WebClientServiceImpl(WebClient.Builder getWebClientBuilder) {
        this.getWebClientBuilder = getWebClientBuilder;
    }

    public FeedBackResponse getFeedBack(FeedbackRequest feedbackRequest){

        FeedBackResponse feedBackResponse = null;

        try{
            ResponseEntity<String> entity = getWebClientBuilder.build().post()
                    .uri("https://87c9ada9-844e-4b87-8dc9-cb02ec4267e3.mock.pstmn.io/feedback")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue(feedbackRequest)
                    .retrieve().toEntity(String.class)
                    .timeout(Duration.ofSeconds(timeout))
                    .block();

            logger.info("Service: {}, info: {}","getFeedBack",entity);
            assert entity != null;
            logger.info("getStatusCode = " + entity.getStatusCode().toString());
            logger.info("getStatusCode isError = " + String.valueOf(entity.getStatusCode().isError()));
            logger.info("getStatusCode is2xxSuccessful = " + String.valueOf(entity.getStatusCode().is2xxSuccessful()));

            if(entity != null && (!entity.getStatusCode().isError() && entity.getStatusCode().is2xxSuccessful())){

                ObjectMapper objectMapper = new ObjectMapper();
                feedBackResponse = objectMapper.readValue(entity.getBody(), FeedBackResponse.class);
                FeedBackRootResponse feedBackRootResponse = new FeedBackRootResponse();
                feedBackRootResponse.setFeedBackResponse(feedBackResponse);
                feedBackRootResponse.setStatusCode(entity.getStatusCode().value());
                logger.info("feedBackResponse" + String.valueOf(feedBackResponse));
                logger.info("feedBackRootResponse " + feedBackRootResponse);

            }else{

                FeedBackRootResponse feedBackRootResponse = new FeedBackRootResponse();
                FeedBackResponse response = new FeedBackResponse();
                response.setMessage("Feedback not submitted");
                feedBackRootResponse.setFeedBackResponse(response);
                feedBackRootResponse.setStatusCode(entity.getStatusCode().value());
                logger.info("feedBackResponse" + String.valueOf(feedBackResponse));
                logger.info("feedBackRootResponse " + feedBackRootResponse);
                feedBackResponse = response;

            }

        }catch (WebClientResponseException webEx){
            if(webEx.getRawStatusCode()!=403) {
                FeedBackResponse response = new FeedBackResponse();
                response.setMessage(webEx.getMessage());
                feedBackResponse = response;
                logger.error("Service Error: {}, trace: {}", "getFeedBack ", webEx.getMessage());
            }
        }
        catch (Exception ex){
            FeedBackResponse response = new FeedBackResponse();
            response.setMessage(ex.getMessage());
            feedBackResponse = response;
            logger.error("Service Error: {}, trace: {}","getFeedBack ",ex.getMessage());
        }

        return feedBackResponse;
    }



}
