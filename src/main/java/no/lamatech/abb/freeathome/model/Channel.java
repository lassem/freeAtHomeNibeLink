package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import no.lamatech.abb.freeathome.model.adapters.DataPointAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "device")
@XmlAccessorType(XmlAccessType.FIELD)
public class Channel {
    @XmlAttribute public String mask;
    @XmlAttribute public String nameId;
    @XmlAttribute public String maxConnections;
    @XmlAttribute public Boolean rebootDeviceOnFunctionChange;
    @XmlAttribute public Boolean sameLocation;
    @XmlAttribute public String i;
    @XmlAttribute public String cid;

//    @JacksonXmlElementWrapper(localName = "attribute", useWrapping = false)
//    @JacksonXmlProperty
//     List<Attribute> attributes;
    //functions

    @XmlElementWrapper(name = "inputs")
    @XmlJavaTypeAdapter(DataPointAdapter.class)
    public Map<String, DataPoint> inputs = Collections.emptyMap();

    @XmlElementWrapper(name = "outputs")
    @XmlJavaTypeAdapter(DataPointAdapter.class)
    public Map<String, DataPoint> outputs = Collections.emptyMap();

    @JacksonXmlElementWrapper(localName = "parameters")
    @JacksonXmlProperty(localName = "parameter")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, shape = JsonFormat.Shape.ARRAY)
    public List<Parameter> parameters = Collections.emptyList();

    //scenes

    public DataPoint getInput(String i) {
        return inputs.get(i);
    }

    public DataPoint getOutput(String i) {
        return outputs.get(i);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "inputs=" + inputs +
                ", outputs=" + outputs +
                '}';
    }
}
