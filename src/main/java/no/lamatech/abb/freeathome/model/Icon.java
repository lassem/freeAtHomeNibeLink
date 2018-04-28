package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Icon {
    @JacksonXmlProperty(isAttribute = true) public String nameId;
    @JacksonXmlProperty(isAttribute = true) public String iconId;
    @JacksonXmlProperty(isAttribute = true) public boolean glow;
    @JacksonXmlProperty(isAttribute = true) public boolean group;
    @JacksonXmlText public String text;
}
