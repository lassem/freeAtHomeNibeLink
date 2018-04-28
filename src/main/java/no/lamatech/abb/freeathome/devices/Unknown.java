package no.lamatech.abb.freeathome.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.lamatech.abb.freeathome.model.Device;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Unknown extends Device {

}
