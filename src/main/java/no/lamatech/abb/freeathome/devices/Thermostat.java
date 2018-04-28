package no.lamatech.abb.freeathome.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.lamatech.abb.freeathome.model.Device;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Thermostat extends Device {

    @Override
    public String toString() {

        return "[" +
                "temperature: " + getTemp() + ""
                + "]";

    }

    public Double getTemp() {
        return Double.valueOf(this.channels.get("ch0000").outputs.get("odp0010").value);
    }

    public Double getTargetTemp() {
        return Double.valueOf(this.channels.get("ch0000").outputs.get("odp0006").value);
    }

    public String getName() {
        return attributes.get("displayName");
    }
}
