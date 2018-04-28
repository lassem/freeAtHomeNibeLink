package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Parameter {
    @JacksonXmlProperty(isAttribute = true) public String i;
    @JacksonXmlProperty(isAttribute = true) public String type;
    @JacksonXmlProperty(isAttribute = true) public String partnerId;
    @JacksonXmlProperty(isAttribute = true) public String condition;
    @JacksonXmlProperty(isAttribute = true) public String nameId;
    @JacksonXmlProperty(isAttribute = true) public String mandatory;
    @JacksonXmlProperty(isAttribute = true) public String functionGroup;
    @JacksonXmlProperty(isAttribute = true) public String pairingId;
    @JacksonXmlProperty(isAttribute = true) public Number minValue;
    @JacksonXmlProperty(isAttribute = true) public Number maxValue;
    @JacksonXmlProperty(isAttribute = true) public Number interval;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "phase") public List<Phase> phases;
}
