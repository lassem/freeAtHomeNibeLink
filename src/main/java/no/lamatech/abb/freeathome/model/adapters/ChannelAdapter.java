package no.lamatech.abb.freeathome.model.adapters;

import no.lamatech.abb.freeathome.model.Channel;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChannelAdapter extends XmlAdapter<List<Channel>, Map<String, Channel>> {
    @Override
    public Map<String, Channel> unmarshal(List<Channel> channels) {
        return channels.stream()
                .collect(Collectors.toMap(channel -> channel.i, channel -> channel));
    }

    @Override
    public List<Channel> marshal(Map<String, Channel> map) {
        return new ArrayList<>(map.values());
    }
}
