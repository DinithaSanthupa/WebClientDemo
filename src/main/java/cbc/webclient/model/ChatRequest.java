package cbc.webclient.model;

import lombok.Data;

@Data
public class ChatRequest {
    private String token;
    private String query;

}
