package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "string")
public class StrValue {
    @JacksonXmlProperty(localName = "nameId", isAttribute = true) public String key;
    @JacksonXmlText public String value;

    public StrValue() {
    }

    public StrValue(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
