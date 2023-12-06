package sample;

import java.util.LinkedList;
import java.util.List;

public class MyMapBucket {
    private List<MyKeyValueEntry> entries;

    public MyMapBucket() {
        if(entries == null) {
            entries = new LinkedList<>();
        }
    }

    public List<MyKeyValueEntry> getEntries() {
        return entries;
    }

    public void addEntry(MyKeyValueEntry entry) {
        this.entries.add(entry);
    }

    public void removeEntry(MyKeyValueEntry entry) {
        this.entries.remove(entry);
    }
}