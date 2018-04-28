package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@JacksonXmlRootElement(localName = "attribute")
public class Attribute {
    @JacksonXmlProperty(localName = "name", isAttribute = true) public String name;
    @JacksonXmlText public String value;

}
