package org.grizz.keeper.service;

import com.google.common.collect.Maps;
import org.grizz.keeper.model.Entry;

import java.util.Map;

public class SystemEntry {

    public static Entry removed() {
        return removedAmount(1l);
    }

    public static Entry removedAmount(Long amount) {
        return getSystemEntry("removed", amount.toString());
    }

    private static Entry getSystemEntry(String key, String value) {
        Map<String, Object> values = Maps.newHashMap();
        values.put(key, values);
        return Entry.builder()
          .key("SYSTEM")
          .value(values)
          .date(System.currentTimeMillis())
          .build();
    }
}
