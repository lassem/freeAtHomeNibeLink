package no.lamatech.abb.freeathome.devices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import no.lamatech.abb.freeathome.model.Device;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Switch extends Device {
    @Override
    public String toString() {
        String state = channels.get("ch0006").outputs.get("odp0000").value;
        String dimmer = channels.get("ch0006").outputs.get("odp0001").value;

        return "Switch{" +
                "state='" + state + '\'' +
                ", dimmer='" + dimmer + '\'' +
                '}';
    }
}
