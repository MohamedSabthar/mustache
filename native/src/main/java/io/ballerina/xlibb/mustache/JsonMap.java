package io.ballerina.xlibb.mustache;

import io.ballerina.runtime.api.async.StrandMetadata;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class converts ballerina map values to the values required by mustache.
 */
class JsonMap implements Map<String, Object> {
    private final BMap<BString, Object> json;
    private final StrandMetadata strandMetadata;

    JsonMap(BMap<BString, Object> json, StrandMetadata strandMetadata) {
        this.json = json;
        this.strandMetadata = strandMetadata;
    }

    @Override
    public Object get(Object key) {
        Object value = json.get(StringUtils.fromString(key.toString()));
        return Utils.convert(value, this);
    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void putAll(Map map) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set<String> keySet() {
        return Arrays.stream(json.getKeys()).map(BString::getValue).collect(Collectors.toSet());
    }

    @Override
    public Collection<Object> values() {
        return json.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return json.entrySet().stream().map(entry -> Map.entry(entry.getKey().getValue(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    @Override
    public int size() {
        return json.size();
    }

    @Override
    public boolean isEmpty() {
        return json.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return json.containsKey(StringUtils.fromString(key.toString()));
    }

    @Override
    public boolean containsValue(Object value) {
        return json.values().contains(value);
    }

    public StrandMetadata getStrandMetadata() {
        return strandMetadata;
    }
}
