package cbc.webclient.serviceImpl;

import cbc.webclient.model.*;
import cbc.webclient.service.WebClientService;
import cbc.webclient.util.Token;
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
import java.util.List;

@Service
public class WebClientServiceImpl implements WebClientService {

    Logger logger = LoggerFactory.getLogger(WebClientServiceImpl.class);
    private WebClient.Builder getWebClientBuilder;
    @Value("${timeout}")
    private int timeout;

    public WebClientServiceImpl(WebClient.Builder getWebClientBuilder) {
        this.getWebClientBuilder = getWebClientBuilder;
    }

    @Override
    public FeedBackRootResponse getFeedBack(FeedbackRequest feedbackRequest){

        FeedBackRootResponse feedBackRootResponse = null;

        try{
            ResponseEntity<String> entity = getWebClientBuilder.build().post()
                    .uri("https://2aa6d7b7-6431-4e9a-a8e2-a373373105b8.mock.pstmn.io/feedback")
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
                FeedBackResponse feedBackResponse = new FeedBackResponse();
                feedBackResponse = objectMapper.readValue(entity.getBody(), FeedBackResponse.class);
                feedBackRootResponse = new FeedBackRootResponse();
                feedBackRootResponse.setFeedBackResponse(feedBackResponse);
                feedBackRootResponse.setStatusCode(entity.getStatusCode().value());
                feedBackRootResponse.setStatusMessage("Success");
                logger.info("feedBackResponse" + String.valueOf(feedBackResponse));
                logger.info("feedBackRootResponse " + feedBackRootResponse);

            }else{

                feedBackRootResponse = new FeedBackRootResponse();
                FeedBackResponse response = new FeedBackResponse();
                response.setMessage("Feedback not submitted");
                feedBackRootResponse.setFeedBackResponse(response);
                feedBackRootResponse.setStatusCode(entity.getStatusCode().value());
                logger.info("feedBackResponse" + String.valueOf(response.getMessage()));
                logger.info("feedBackRootResponse " + feedBackRootResponse);

            }

        }catch (WebClientResponseException webEx){
            if(webEx.getRawStatusCode()!=403) {
                FeedBackResponse response = new FeedBackResponse();
                response.setMessage(webEx.getMessage());
                feedBackRootResponse = new FeedBackRootResponse();
                feedBackRootResponse.setFeedBackResponse(response);
                feedBackRootResponse.setStatusCode(webEx.getStatusCode().value());
                feedBackRootResponse.setStatusMessage("WebClientResponseException " + webEx.getMessage());
                logger.error("Service Error: {}, trace: {}", "getFeedBack ", webEx.getMessage());
            }
        }
        catch (Exception ex){
            FeedBackResponse response = new FeedBackResponse();
            response.setMessage(ex.getMessage());
            feedBackRootResponse = new FeedBackRootResponse();
            feedBackRootResponse.setFeedBackResponse(response);
            feedBackRootResponse.setStatusMessage("WebClientResponseException " + ex.getMessage());

            logger.error("Service Error: {}, trace: {}","getFeedBack ",ex.getMessage());
        }

        return feedBackRootResponse;
    }
    @Override
    public ChatRootResponse getChatResponse(String query) {

        ChatRootResponse chatRootResponse = null;

        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setQuery(query);

        Token token = new Token();
        chatRequest.setToken(token.generateToken());

        try{
            ResponseEntity<String> entity = getWebClientBuilder.build().post()
                    .uri("https://2aa6d7b7-6431-4e9a-a8e2-a373373105b8.mock.pstmn.io/chat")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue(chatRequest)
                    .retrieve().toEntity(String.class)
                    .timeout(Duration.ofSeconds(timeout))
                    .block();

            logger.info("Service: {}, info: {}","getChatHiResponse",entity);
            assert entity != null;
            logger.info("getStatusCode = " + entity.getStatusCode().toString());
            logger.info("getStatusCode isError = " + String.valueOf(entity.getStatusCode().isError()));
            logger.info("getStatusCode is2xxSuccessful = " + String.valueOf(entity.getStatusCode().is2xxSuccessful()));

            if(entity != null && (!entity.getStatusCode().isError() && entity.getStatusCode().is2xxSuccessful())){

                ObjectMapper objectMapper = new ObjectMapper();
                ChatResponse chatResponse = new ChatResponse();
                chatResponse = objectMapper.readValue(entity.getBody(), ChatResponse.class);
                logger.info("getChatHiResponse" + String.valueOf(chatResponse));
                List<DocumentInfo> docs = chatResponse.getDocs();

                for (DocumentInfo documentInfo : docs) {
                    String path = documentInfo.getPath();
                    int page = documentInfo.getPage();
                    logger.info("Path: " + path);
                    logger.info("Page: " + page);
                }
                chatRootResponse = new ChatRootResponse();
                chatRootResponse.setChatResponse(chatResponse);
                chatRootResponse.setStatusCode(entity.getStatusCode().value());
                chatRootResponse.setStatusMessage("Success");
                logger.info("getChatHiResponse" + String.valueOf(chatResponse));
                logger.info("getChatHiRootResponse " + chatRootResponse);

            }else{

                chatRootResponse = new ChatRootResponse();
                chatRootResponse.setStatusMessage("Message not send successfully");
                chatRootResponse.setStatusCode(entity.getStatusCode().value());
                logger.info("chatHiRootResponse " + chatRootResponse);
            }

        }catch (WebClientResponseException webEx){
            if(webEx.getRawStatusCode()!=403) {
                logger.error("Service Error: {}, trace: {}", "getChatHiResponse ", webEx.getMessage());
                chatRootResponse = new ChatRootResponse();
                chatRootResponse.setStatusCode(webEx.getStatusCode().value());
                chatRootResponse.setStatusMessage(webEx.getMessage());
                chatRootResponse.setStatusMessage(" "+ webEx.getMessage());
            }
        }
        catch (Exception ex){
            logger.error("Service Error: {}, trace: {}", "getChatHiResponse ", ex.getMessage());
            chatRootResponse = new ChatRootResponse();
            chatRootResponse.setStatusMessage(ex.getMessage());
            chatRootResponse.setStatusMessage(" "+ ex.getMessage());
        }

        return chatRootResponse;
    }

}
