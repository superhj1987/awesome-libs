package me.rowkey.libs.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Bryant.Hang on 2017/5/26.
 */
public final class Maps {
    private static final ObjectMapper mapper = new ObjectMapper();

    private Maps() {
    }

    /**
     * Serialize an object to map
     *
     * @param object the target object
     * @return the map
     */
    public static Map<?, ?> toMap(Object object) {
        return mapper.convertValue(object, Map.class);
    }

    /**
     * Deserialize the map to an object
     *
     * @param fromMap    the map
     * @param targetType the object's class
     * @param <T>        generic type
     * @return the object
     */
    public static <T> T fromMap(Map<?, ?> fromMap, Class<T> targetType) {
        return mapper.convertValue(fromMap, targetType);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return com.google.common.collect.Maps.newHashMap();
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(
            int expectedSize) {
        return com.google.common.collect.Maps.newHashMapWithExpectedSize(expectedSize);
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return com.google.common.collect.Maps.newConcurrentMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return com.google.common.collect.Maps.newTreeMap();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return com.google.common.collect.Maps.newTreeMap(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return com.google.common.collect.Maps.newLinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(
            Map<? extends K, ? extends V> map) {
        return com.google.common.collect.Maps.newLinkedHashMap(map);
    }
}
