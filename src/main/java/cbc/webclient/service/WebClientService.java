package cbc.webclient.service;

import cbc.webclient.model.Cat;
import cbc.webclient.model.ClientRootResponse;
import cbc.webclient.model.MacBookPro;

public interface WebClientService {

    public ClientRootResponse addClientDetails(MacBookPro cat);

}
