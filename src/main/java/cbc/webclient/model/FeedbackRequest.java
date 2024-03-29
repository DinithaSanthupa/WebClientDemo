package cbc.webclient.model;

import lombok.Data;

@Data
public class FeedbackRequest {

    private String query;
    private String generated_answer;
    private String feedback;

}
