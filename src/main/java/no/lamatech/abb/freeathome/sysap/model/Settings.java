package no.lamatech.abb.freeathome.sysap.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Settings {
    @JsonProperty("flags") public Flags flags = new Flags();
    @JsonProperty("users") public List<User> users = Collections.emptyList();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Flags {
        @JsonProperty("version") public String version;
        @JsonProperty("build") public String build;
        @JsonProperty("sessionId") public String sessionId;
        @JsonProperty("locale") public String locale;
        @JsonProperty("fahrenheit") public boolean fahrenheit = false;
        @JsonProperty("logging") public boolean logging = false;
        @JsonProperty("name") public String name;
        @JsonProperty("serialNumber") public String serialNumber;
        @JsonProperty("abb") public boolean abb = false;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        @JsonProperty("name") public String name;
        @JsonProperty("role") public String role;
        @JsonProperty("jid") public String jid;
        @JsonDeserialize(using = StringBooleanDeserializer.class)
        @JsonProperty("initial") public boolean initial = false;
        @JsonDeserialize(using = StringBooleanDeserializer.class)
        @JsonProperty("enabled") public boolean enabled = false;
    }

    public static class StringBooleanDeserializer extends JsonDeserializer<Boolean> {
        @Override
        public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return "true".equals(p.getText());
        }
    }
}
