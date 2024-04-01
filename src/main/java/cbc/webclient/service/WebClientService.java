package cbc.webclient.service;

import cbc.webclient.model.*;

public interface WebClientService {
    public FeedBackRootResponse getFeedBack(FeedbackRequest feedbackRequest);
    public ChatRootResponse getChatResponse(String query);

}
