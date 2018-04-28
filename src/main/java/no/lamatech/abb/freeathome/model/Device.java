package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import no.lamatech.abb.freeathome.devices.Switch;
import no.lamatech.abb.freeathome.devices.Thermostat;
import no.lamatech.abb.freeathome.devices.Unknown;
import no.lamatech.abb.freeathome.model.adapters.ChannelAdapter;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "deviceId",
        defaultImpl = Unknown.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "9019", value = Switch.class),
        @JsonSubTypes.Type(name = "1004", value = Thermostat.class),
        @JsonSubTypes.Type(name = "9004", value = Thermostat.class)
})
public class Device {
    @XmlAttribute(name = "shortSerialNumber") public String shortSerialNumber;
    @XmlAttribute(name = "nameId") public String nameId;
    @XmlAttribute(name = "functionId") public String functionId;
    @XmlAttribute(name = "iconId") public String iconId;
    @XmlAttribute(name = "deviceId") public String deviceId = "FFFF";
    @XmlAttribute(name = "softwareVersion") public String softwareVersion;
    @XmlAttribute(name = "domainAddress") public String domainAddress;
    @XmlAttribute(name = "compilerVersion") public String compilerVersion;
    @XmlAttribute(name = "buildNumber") public String buildNumber;
    @XmlAttribute(name = "serialNumber") public String serialNumber;
    @XmlAttribute(name = "commissioningState") public String commissioningState;
    @XmlAttribute(name = "copyId") public String copyId;
    @XmlAttribute(name = "progress") public String progress;

    @JacksonXmlProperty(localName = "attribute")
    @JacksonXmlElementWrapper(localName = "attributes", useWrapping = false)
    public Map<String, String> attributes = new HashMap<>();

    @JsonSetter
    @JsonAnySetter
    public void setAttributes(List<Attribute> attributes) {
        attributes.forEach(attribute -> this.attributes.put(attribute.name, attribute.value));
    }

//    @JacksonXmlElementWrapper(useWrapping = false)
//    @JacksonXmlProperty(localName = "attribute")
//    @JsonDeserialize(converter = Attributes.SysApDeserializer.class)
//    Map<String, String> attributes;

//    @JacksonXmlElementWrapper(localName = "channels"
//    @JacksonXmlProperty(localName = "channel")
//    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)

    @XmlElementWrapper(name = "channels")
    @XmlJavaTypeAdapter(ChannelAdapter.class)
    public Map<String, Channel> channels;

    public Channel getChannel(String i) {
        return channels.get(i);
    }

//    @Override
//    public String toString() {
//        return "Device{" +
//                "deviceId='" + deviceId + '\'' +
//                ", serialNumber='" + serialNumber + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", channels=" + channels +
                '}';
    }
}
