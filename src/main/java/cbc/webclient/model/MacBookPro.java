package cbc.webclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacBookPro {

    @JsonProperty("name")
    private String name;

    @JsonProperty("year")
    private int year;

    @JsonProperty("price")
    private double price;

    @JsonProperty("cpuModel")
    private String cpuModel;

    @JsonProperty("hardDiskSize")
    private String hardDiskSize;

}
