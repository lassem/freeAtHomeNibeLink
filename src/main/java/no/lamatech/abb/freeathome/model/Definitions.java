package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Definitions {
    @JacksonXmlProperty(localName = "icons") public List<Icon> icons;
    @JacksonXmlProperty(localName = "functions") public List<Function> functions;
    @JacksonXmlProperty(localName = "functionGroups") public List<FunctionGroup> functionGroups;
    @JacksonXmlProperty(localName = "preconditions") public List<Template> preconditions;
//    @JacksonXmlProperty(localName = "roomPresets") public List<RoomPreset> roomPresets;
//    @JacksonXmlProperty(localName = "floorPresets") public List<FloorPreset> floorPresets;
//    @JacksonXmlProperty(localName = "presim") public List<FloorPreset> presim;
}
