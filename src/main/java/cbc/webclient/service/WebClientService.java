package cbc.webclient.service;

import cbc.webclient.model.*;

public interface WebClientService {
    public FeedBackResponse getFeedBack(FeedbackRequest feedbackRequest);
    public ChatRootResponse getChatResponse(String query);

}
