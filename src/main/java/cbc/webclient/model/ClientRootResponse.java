package cbc.webclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class ClientRootResponse {

    @JsonProperty("responseCodes")
    public List<ResponseCode> responseCodes;
    private String traceNumber;
    private Integer status;
    @Data
    public static class ResponseCode{
        @JsonProperty("responseCode")
        public String responseCode;
        @JsonProperty("description")
        public String description;
        @JsonProperty("info")
        public String info;
    }
}
