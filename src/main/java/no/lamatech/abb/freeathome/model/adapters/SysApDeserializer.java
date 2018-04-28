package no.lamatech.abb.freeathome.model.adapters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import no.lamatech.abb.freeathome.model.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SysApDeserializer implements Converter<List<Value>, Map<String, String>> {
    @Override
    public Map<String, String> convert(List<Value> value) {
        return value.stream()
                .filter(Objects::nonNull)
                .filter(v -> v.value != null)
                .collect(Collectors.toMap(v -> v.key, v -> v.value));
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructCollectionType(List.class, Value.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructMapType(HashMap.class, String.class, String.class);
    }
}
