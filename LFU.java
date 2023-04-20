package test;


import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;


public class LFU implements CacheReplacementPolicy {
    LinkedHashMap<String, Integer> words;
    LinkedList<String> list;


    public LFU() {
        this.words = new LinkedHashMap<>();
        this.list = new LinkedList<>();
    }

    @Override
    public void add(String word) {
        list.remove(word);
        list.add(word);
        if (!words.containsKey(word)) {
            words.put(word, 1);
        } else {
            words.put(word, words.get(word) + 1);
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return words.get(o1) - words.get(o2);
            }
        });
    }

    @Override
    public String remove() {
        String lfu = list.iterator().next();
        words.remove(lfu);
        list.remove(lfu);
        return lfu;
    }
}