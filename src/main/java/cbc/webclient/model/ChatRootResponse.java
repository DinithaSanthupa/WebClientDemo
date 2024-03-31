package cbc.webclient.model;

import lombok.Data;

@Data
public class ChatRootResponse {
    private Integer statusCode;
    private ChatResponse chatResponse;
    private String statusMessage;
}
