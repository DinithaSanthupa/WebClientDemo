package cbc.webclient.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private String response;
    private List<Object> docs;
}
