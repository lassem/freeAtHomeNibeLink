package no.lamatech.nibe;

import de.fuerstenau.buildconfig.BuildConfig;
import io.reactivex.Observable;
import no.lamatech.abb.freeathome.ProjectManager;
import no.lamatech.abb.freeathome.devices.Thermostat;
import no.lamatech.nibe.api.UplinkService;
import no.lamatech.nibe.api.model.SetThermostat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ThermostatService {
    private static final Logger log = Logger.getLogger(ThermostatService.class.getSimpleName());
    private final UplinkService uplinkService;
    private final ProjectManager projectManager;

    @Autowired
    public ThermostatService(UplinkService uplinkService, ProjectManager projectManager) {
        this.uplinkService = uplinkService;
        this.projectManager = projectManager;

//
//        uplinkService.getSoftwareInfo("06531916018002").subscribe(result -> log.info(result.toString()), throwable -> {
//            log.info(throwable.toString());
//        });
//
//        uplinkService.getSystems().subscribe(result -> log.info(result.toString()), throwable -> {
//            log.info(throwable.toString());
//        });
//
//        uplinkService.updateThermostat("25849", new SetThermostat() {{
//            this.name = "Kontoret";
//            this.externalId = "1337";
//            this.targetTemp = 210;
//            this.actualTemp = 202;
//            this.valvePosition = null;
//        }})
//                .subscribe(onNext -> log.info("" + onNext.code()), throwable -> log.info(throwable.toString()));

//        Observable.interval(5000, 50000, TimeUnit.MILLISECONDS)
//                .flatMapIterable(ignored -> getThermostats())
//                .map(this::convertToNibeThermostat)
//                .flatMapSingle(thermostat -> uplinkService.updateThermostat(BuildConfig.nibeSystemId, thermostat))
//                .subscribe(onNext -> log.info("" + onNext.code()), throwable -> log.info(throwable.toString()), () -> log.info("complete!"));

        Observable.fromIterable(getThermostats())
                .map(this::convertToNibeThermostat)
                .flatMapSingle(thermostat -> uplinkService.updateThermostat(BuildConfig.nibeSystemId, thermostat))
                .subscribe(onNext -> log.info("" + onNext.code()), throwable -> log.info(throwable.toString()), () -> log.info("complete!"));

        projectManager.thermostatUpdates()
                .map(this::convertToNibeThermostat)
                .flatMapSingle(thermostat -> uplinkService.updateThermostat(BuildConfig.nibeSystemId, thermostat))
                .subscribe(onNext -> log.info("" + onNext.code()), throwable -> log.info(throwable.toString()), () -> log.info("complete!"));
    }

    List<Thermostat> getThermostats() {
        return projectManager.getProject().devices.values().stream()
                .filter(device -> device instanceof Thermostat)
                .map(device -> (Thermostat) device)
                .collect(Collectors.toList());
    }

    SetThermostat convertToNibeThermostat(Thermostat thermostat) {
        SetThermostat newThermostat = new SetThermostat();
        newThermostat.name = thermostat.getName();
        newThermostat.actualTemp = (int) (thermostat.getTemp() * 10);
        newThermostat.targetTemp = (int) (thermostat.getTargetTemp() * 10);
        newThermostat.externalId = "" + thermostat.serialNumber.hashCode();
        newThermostat.valvePosition = null;
        return newThermostat;
    }
}
