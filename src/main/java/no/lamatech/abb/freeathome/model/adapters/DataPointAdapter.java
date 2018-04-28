package no.lamatech.abb.freeathome.model.adapters;

import no.lamatech.abb.freeathome.model.DataPoint;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataPointAdapter extends XmlAdapter<List<DataPoint>, Map<String, DataPoint>> {
    @Override
    public Map<String, DataPoint> unmarshal(List<DataPoint> dataPoints) throws Exception {
        return dataPoints.stream()
                .collect(Collectors.toMap(datapoint -> datapoint.i, dataPoint -> dataPoint));
    }

    @Override
    public List<DataPoint> marshal(Map<String, DataPoint> v) throws Exception {
        return new ArrayList<>(v.values());
    }
}
