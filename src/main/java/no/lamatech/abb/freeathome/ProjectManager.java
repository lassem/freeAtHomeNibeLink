package no.lamatech.abb.freeathome;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import no.lamatech.abb.freeathome.devices.Thermostat;
import no.lamatech.abb.freeathome.model.Channel;
import no.lamatech.abb.freeathome.model.DataPoint;
import no.lamatech.abb.freeathome.model.Device;
import no.lamatech.abb.freeathome.model.Project;

import java.io.IOException;
import java.util.logging.Logger;

public class ProjectManager {
    private static final Logger log = Logger.getLogger(ProjectManager.class.getSimpleName());
    private final ObjectReader reader;
    private Project project;
    private final PublishSubject<Thermostat> thermostatUpdates = PublishSubject.create();

    public ProjectManager(XmlMapper xmlMapper) {
        this.reader =  xmlMapper.readerFor(Project.class);
    }

    public Project createWithGetAll(String data) throws IOException {
        project = reader.readValue(data);
        return project;
    }

    // Update project; the naive approach
    public Project update(String update) throws IOException {
        if (project != null) {
            Project delta = reader.readValue(update);
            if (delta != null) {
                for (Device device : delta.devices.values()) {

                    if (device.channels != null) {
                        for (Channel channel : device.channels.values()) {
                            final Device updatedDevice = project.getDevice(device.serialNumber);
                            // check if "state == modified?
                            if (channel.inputs != null) {
                                for (DataPoint input : channel.inputs.values()) {
                                    DataPoint oldInput = updatedDevice.getChannel(channel.i).getInput(input.i);
                                    oldInput.value = input.value;
                                }
                            }
                            if (channel.outputs != null) {
                                // check if "state == modified?
                                for (DataPoint output : channel.outputs.values()) {
                                    DataPoint oldOutput = updatedDevice.getChannel(channel.i).getOutput(output.i);
                                    oldOutput.value = output.value;
                                }
                            }

                            if (updatedDevice instanceof Thermostat) {
                                thermostatUpdates.onNext((Thermostat) updatedDevice);
                            }

                            log.info("Updated device: " + updatedDevice.toString());
                        }
                    }
                }
            }
        }
        return project;
    }

    public Project getProject() {
        return project;
    }

    public Observable<Thermostat> thermostatUpdates() {
        return thermostatUpdates;
    }
}
