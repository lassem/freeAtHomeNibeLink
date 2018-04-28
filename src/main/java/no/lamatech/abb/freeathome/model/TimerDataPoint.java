package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimerDataPoint {
    @JacksonXmlProperty(isAttribute = true) public String pairingId;
    @JacksonXmlProperty(isAttribute = true) public String mask;
}
