package cbc.webclient.serviceImpl;

import cbc.webclient.controller.WebController;
import cbc.webclient.model.Cat;
import cbc.webclient.model.ClientRootResponse;
import cbc.webclient.model.MacBookPro;
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
    @Override
    public ClientRootResponse addClientDetails(MacBookPro macBookPro) {

        ClientRootResponse response = null;
        try {
            ResponseEntity<String> entity = getWebClientBuilder.build().post()
                    .uri("https://api.restful-api.dev/objects")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .bodyValue(macBookPro)
                    .retrieve().toEntity(String.class)
                    .timeout(Duration.ofSeconds(timeout))
                    .block();
            if (entity != null && (!entity.getStatusCode().isError() && entity.getStatusCode().is2xxSuccessful())) {

                logger.info(String.valueOf(entity));
                String traceNumber = getTraceNo(entity.getHeaders());
                logger.info("********************************************************");
                logger.info(String.valueOf(entity.getHeaders()));
                logger.info("********************************************************");
                logger.info(entity.getStatusCode().toString());
                logger.info("tracenumber" + traceNumber);
                ObjectMapper objectMapper = new ObjectMapper();
                response = objectMapper.readValue(entity.getBody(), ClientRootResponse.class);
                response.setTraceNumber(traceNumber);
                response.setStatus(entity.getStatusCode().value());
                logger.info(String.valueOf(response.getStatus()));
            }else {
//                response = new ClientRootResponse();
//                response.setStatus(entity.getStatusCode().value());
//                String traceNumber = getTraceNo(entity.getHeaders());
//                response.setTraceNumber(traceNumber);
            }
        }catch (WebClientResponseException webEx){
            if(webEx.getRawStatusCode()!=403){
                response = new ClientRootResponse();
                response.setStatus(webEx.getRawStatusCode());
                logger.error("********************************************************");
                logger.error("WebClientResponseException");
                logger.error(webEx.getMessage());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    private String getTraceNo(HttpHeaders headers){
        if (headers!=null && headers.get("TraceNumber")!=null){
            return headers.get("TraceNumber").get(0);
        }
        return "";
    }



}
