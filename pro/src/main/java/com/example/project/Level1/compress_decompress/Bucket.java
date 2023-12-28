package com.example.project.Level1.compress_decompress;

import java.util.LinkedList;
import java.util.List;

public class Bucket {
    private List<KeyValue> entries;

    public Bucket() {
        if(entries == null) {
            entries = new LinkedList<>();
        }
    }

    public List<KeyValue> getEntries() {
        return entries;
    }

    public void addEntry(KeyValue entry) {
        this.entries.add(entry);
    }

    public void removeEntry(KeyValue entry) {
        this.entries.remove(entry);
    }
}