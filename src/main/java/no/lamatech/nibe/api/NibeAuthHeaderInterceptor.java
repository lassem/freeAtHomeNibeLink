package no.lamatech.nibe.api;

import de.fuerstenau.buildconfig.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class NibeAuthHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .header("Authorization", "Bearer " + BuildConfig.nibeUplinkToken)
                .build());
    }
}
