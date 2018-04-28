package no.lamatech;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import no.lamatech.abb.freeathome.ProjectManager;
import no.lamatech.abb.freeathome.sysap.SysApService;
import no.lamatech.nibe.api.NibeAuthHeaderInterceptor;
import no.lamatech.nibe.api.UplinkService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rocks.xmpp.core.session.ConnectionConfiguration;
import rocks.xmpp.core.session.TcpConnectionConfiguration;

@org.springframework.context.annotation.Configuration
public class Configuration {
    private static final String sysAp = "192.168.10.215";

    @Bean
    ConnectionConfiguration provideXmppConnectionConfiguration() {
        return TcpConnectionConfiguration.builder()
                .hostname(sysAp)
                .secure(false)
                .port(5222)
                .build();
    }

    @Bean
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .build();
    }

    @Bean
    Retrofit.Builder provideRetrofitBuilder(OkHttpClient httpClient, ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper));
    }

    @Bean
    SysApService provideSysApService(Retrofit.Builder builder) {
        return builder.baseUrl("http://" + sysAp).build().create(SysApService.class);
    }

    @Bean
    UplinkService provideUplinkService(Retrofit.Builder builder, OkHttpClient httpClient) {
        OkHttpClient newClient = httpClient.newBuilder().addInterceptor(new NibeAuthHeaderInterceptor()).build();

        return builder.baseUrl("https://api.nibeuplink.com/api/v1/").client(newClient).build().create(UplinkService.class);
    }

    @Bean
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper()
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                .disable(
                        MapperFeature.AUTO_DETECT_CREATORS,
                        MapperFeature.AUTO_DETECT_FIELDS,
                        MapperFeature.AUTO_DETECT_SETTERS,
                        MapperFeature.AUTO_DETECT_GETTERS,
                        MapperFeature.AUTO_DETECT_IS_GETTERS,
                        MapperFeature.DEFAULT_VIEW_INCLUSION
                )
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .registerModule(new KotlinModule());
    }

    @Bean
    ProjectManager provideProjectManager() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.registerModules(
                new JacksonXmlModule(),
                new JaxbAnnotationModule().setPriority(JaxbAnnotationModule.Priority.SECONDARY)
        );
        return new ProjectManager(mapper);
    }
}
