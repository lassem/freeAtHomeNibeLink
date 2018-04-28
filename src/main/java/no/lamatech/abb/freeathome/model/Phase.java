package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Phase {
    @JacksonXmlProperty(isAttribute = true) public String key;
    @JacksonXmlProperty(isAttribute = true) public String value;
    @JacksonXmlProperty(isAttribute = true, localName = "default") public String defaultValue;
    @JacksonXmlProperty(isAttribute = true) public String nameId;
}
