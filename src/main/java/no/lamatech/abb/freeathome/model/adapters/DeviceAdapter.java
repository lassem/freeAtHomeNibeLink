package no.lamatech.abb.freeathome.model.adapters;

import no.lamatech.abb.freeathome.model.Device;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeviceAdapter extends XmlAdapter<List<Device>, Map<String, Device>> {
    @Override
    public Map<String, Device> unmarshal(List<Device> devices) {
        return devices.stream()
                .collect(Collectors.toMap(device -> device.serialNumber, device -> device));
    }

    @Override
    public List<Device> marshal(Map<String, Device> map) {
        return new ArrayList<>(map.values());
    }
}
