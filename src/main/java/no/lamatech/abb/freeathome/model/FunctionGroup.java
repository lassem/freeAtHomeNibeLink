package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FunctionGroup {
    @JacksonXmlProperty(isAttribute = true) public String context;
    @JacksonXmlProperty(isAttribute = true) public String type;
    @JacksonXmlProperty(isAttribute = true) public String nameId;
    @JacksonXmlProperty(isAttribute = true) public String iconId;
    @JacksonXmlProperty(isAttribute = true) public boolean noSwitching;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "function") public List<Function> functions;
}
