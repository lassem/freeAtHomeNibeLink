package no.lamatech.abb.freeathome.model.adapters;

import no.lamatech.abb.freeathome.model.StrValue;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringsAdapter extends XmlAdapter<List<StrValue>, Map<String, String>> {
    @Override
    public Map<String, String> unmarshal(List<StrValue> strings) {
        return strings.stream()
                .collect(Collectors.toMap(string -> string.key, string -> string.value));
    }

    @Override
    public List<StrValue> marshal(Map<String, String> map) {
        return map.entrySet().stream()
                .map(entry -> new StrValue(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

    }
}
