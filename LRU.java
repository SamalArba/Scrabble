package test;

import java.util.LinkedHashSet;


public class LRU implements CacheReplacementPolicy {
    LinkedHashSet<String> list;

    public LRU() {
        this.list = new LinkedHashSet<>();
    }

    @Override
    public void add(String word) {
        list.remove(word);
        list.add(word);
    }

    @Override
    public String remove() {
        if (list.isEmpty()){
            return null;
        }
        String copy = list.iterator().next();
        list.remove(copy);
        return copy;
    }
}
