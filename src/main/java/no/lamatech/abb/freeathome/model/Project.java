package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import no.lamatech.abb.freeathome.model.adapters.DeviceAdapter;
import no.lamatech.abb.freeathome.model.adapters.StringsAdapter;
import no.lamatech.abb.freeathome.model.adapters.SysApDeserializer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "project")
public class Project {
    @JsonFormat(pattern = "yyyymmddhhmmssSSS")
    @JacksonXmlProperty(localName = "time", isAttribute = true) public Date time;
    @XmlAttribute(name = "timeStamp") public Long timeStamp;
    @XmlAttribute(name = "sessionId") public String sessionId;
    @XmlAttribute(name = "type") public String type;
    @XmlAttribute(name = "mrhaVersion") public String mrhaVersion;
    @XmlAttribute(name = "mrhaBuild") public String mrhaBuild;
    @XmlElement(name = "sunRiseTimes") public List<String> sunRiseTimes;
    @XmlElement(name = "sunSetTimes") public String sunSetTimes;
    @XmlElement(name = "definitions") public Definitions definitions;

    @XmlElementWrapper(name = "strings")
    @XmlJavaTypeAdapter(StringsAdapter.class)
    public Map<String, String> strings;

    @JsonDeserialize(converter = SysApDeserializer.class)
    @JacksonXmlProperty(localName = "sysap")
    public Map<String, String> sysap;

    @XmlElementWrapper(name = "devices")
    @XmlJavaTypeAdapter(DeviceAdapter.class)
    public Map<String, Device> devices;

    //timerPrograms
    //backups

    public Device getDevice(String serialNumber) {
        return devices.get(serialNumber);
    }

}
