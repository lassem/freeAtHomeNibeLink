package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {
    @JacksonXmlProperty(isAttribute = true) public String id;
    @JacksonXmlProperty(isAttribute = true) public String type;
    @JacksonXmlProperty(isAttribute = true) public String nameId;
    @JacksonXmlProperty(isAttribute = true) public String matchCode;
    @JacksonXmlProperty(isAttribute = true) public String iconId;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "parameter")
    public List<Parameter> parameters;
}
