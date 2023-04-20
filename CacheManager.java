package test;


import java.util.HashSet;

public class CacheManager {
    final int size;
    CacheReplacementPolicy cachePolicy;
    HashSet<String> cache;

    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.cachePolicy = crp;
        this.size = size;
        this.cache = new HashSet<>();
    }

    public boolean query(String word) {
        if (cache.contains(word)) {
            return true;
        }
        return false;
    }

    public void add(String word) {
        this.cachePolicy.add(word);
        this.cache.add(word);
        if (cache.size() > size) {
            this.cache.remove(cachePolicy.remove());
        }
    }
}