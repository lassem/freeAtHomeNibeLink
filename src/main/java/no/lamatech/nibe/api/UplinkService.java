package no.lamatech.nibe.api;

import io.reactivex.Single;
import no.lamatech.nibe.api.model.SetThermostat;
import no.lamatech.nibe.api.model.SoftwareInfo;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

public interface UplinkService {
    // 06531916018002

    @GET("software")
    Single<SoftwareInfo> getSoftwareInfo(@Query("serialNumber") String serialNumber);

    @GET("systems")
    Single<Response<Map>> getSystems();

    @POST("systems/{systemId}/smarthome/thermostats")
    Single<Response<Void>> updateThermostat(@Path("systemId") String systemId, @Body SetThermostat body);
}
