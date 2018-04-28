package no.lamatech.nibe.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetThermostat {
    @JsonProperty("externalId") public String externalId;
    @JsonProperty("name") public String name;
    @JsonProperty("actualTemp") public int actualTemp;
    @JsonProperty("targetTemp") public int targetTemp;
    @JsonProperty("valvePosition") public Integer valvePosition;
}
