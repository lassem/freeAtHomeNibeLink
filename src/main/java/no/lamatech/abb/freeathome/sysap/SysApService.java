package no.lamatech.abb.freeathome.sysap;

import io.reactivex.Single;
import no.lamatech.abb.freeathome.sysap.model.Settings;
import retrofit2.http.GET;

public interface SysApService {

    @GET("/settings.json")
    Single<Settings> getSettings();
}
