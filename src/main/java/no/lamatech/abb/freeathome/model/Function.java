package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "functions")
public class Function {
    @JacksonXmlProperty(isAttribute = true) public String nameId;
    @JacksonXmlProperty(isAttribute = true) public String functionId;
    @JacksonXmlProperty(isAttribute = true) public String name;
    @JacksonXmlProperty(isAttribute = true) public boolean hidden;
    @JacksonXmlProperty(isAttribute = true) public boolean noAutomation;
    @JacksonXmlProperty(isAttribute = true) public boolean noSimulation;
    @JacksonXmlProperty(isAttribute = true) public boolean noAction;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "icon") public List<Icon> icons;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "timerDataPoint") public List<TimerDataPoint> timerDataPoints;
}
