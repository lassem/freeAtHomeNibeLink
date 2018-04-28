package no.lamatech.nibe.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoftwareInfo {
    @JsonProperty("releaseDate") public String releaseDate;
    @JsonProperty("name") public String name;
    @JsonProperty("version") public int version;
    @JsonProperty("release") public int release;
}
