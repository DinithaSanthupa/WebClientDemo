package cbc.webclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client{
    @JsonProperty("participantID")
    public String participantID;

    @JsonProperty("clientID")
    public String clientID;

    @JsonProperty("clientType")
    public String clientType;

    @JsonProperty("citizenID")
    public String citizenID;

    @JsonProperty("searchName")
    public String searchName;

    @JsonProperty("firstName")
    public String firstName;

    @JsonProperty("lastName")
    public String lastName;

    @JsonProperty("defaultPrimaryClientID")
    public String defaultPrimaryClientID;

    @JsonProperty("defaultClientID")
    public String defaultClientID;

    @JsonProperty("recordType")
    public String recordType;
}
