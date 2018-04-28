package no.lamatech.abb.freeathome.model;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

import java.util.*;
import java.util.stream.Collectors;

class Attributes {
    public static class Deserializer implements Converter<List<Attribute>, Map<String, String>> {
        @Override
        public Map<String, String> convert(List<Attribute> attributes) {
            return attributes.stream()
                    .collect(Collectors.toMap(attr -> attr.name, attr -> attr.value));
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return typeFactory.constructCollectionType(List.class, Attribute.class);
        }

        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return typeFactory.constructMapType(HashMap.class, String.class, String.class);
        }
    }
}
