package com.example.project.Level1.compress_decompress;

import java.util.Objects;

public class HMap<K, V> {
    private int CAPACITY = 10;
    private Bucket[] bucket;
    private int size = 0;

    public HMap() {
        this.bucket = new Bucket[CAPACITY];
    }
    private int getHash(K key) {
        return (key.hashCode() & 0xfffffff) % CAPACITY;
    }

    private KeyValue getEntry(K key) {
        int hash = getHash(key);
        for (int i = 0; i < bucket[hash].getEntries().size(); i++) {
            KeyValue keyValue = bucket[hash].getEntries().get(i);
            if(keyValue.getKey().equals(key)) {
                return keyValue;
            }
        }
        return null;
    }
    public void put(K key, V value) {
        if(containsKey(key)) {
            KeyValue entry = getEntry(key);
            entry.setValue(value);
        } else {
            int hash = getHash(key);
            if(bucket[hash] == null) {
                bucket[hash] = new Bucket();
            }
            bucket[hash].addEntry(new KeyValue<>(key, value));
            size++;
        }
    }

    public V get(K key) {
        return containsKey(key) ? (V) getEntry(key).getValue() : null;
    }

    public boolean containsKey(K key) {
        int hash = getHash(key);
        return !(Objects.isNull(bucket[hash]) || Objects.isNull(getEntry(key)));
    }

    public void delete(K key) {
        if(containsKey(key)) {
            int hash = getHash(key);
            bucket[hash].removeEntry(getEntry(key));
            size--;
        }
    }
    public int size() {
        return size;
    }
}
