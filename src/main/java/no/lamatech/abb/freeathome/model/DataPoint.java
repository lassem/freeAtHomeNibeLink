package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataPoint {
    @XmlAttribute(name = "i") public String i;
    @XmlAttribute(name = "full") public Boolean full;
    @XmlElement(name = "value") public String value;
    @XmlElement(name = "state") public String state;

    @Override
    public String toString() {
        return "DataPoint{" +
                "i='" + i + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
