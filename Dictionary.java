package test;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    CacheManager exists;
    CacheManager notExists;
    BloomFilter bf;
    String[] files;

    public Dictionary(String... fileNames) {
        this.exists = new CacheManager(400, new LRU());
        this.notExists = new CacheManager(100, new LFU());
        this.bf = new BloomFilter(256, "md5", "sha1");
        this.files = new String[fileNames.length];
        System.arraycopy(fileNames, 0, this.files, 0, fileNames.length);

        for (String f : fileNames) {
            File file = new File(f);
            Scanner sc = null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String line1 = sc.nextLine();
            String[] Splitted = line1.split(" ");
            for (String w : Splitted) {
                exists.add(w);
                bf.add(w);
            }
        }
    }

    public boolean query(String w) {
        if (exists.query(w)) {
            return true;
        }

        if (notExists.query(w)) {
            notExists.add(w);
            return false;
        }
        if (!bf.contains(w)) {
            notExists.add(w);
            return false;
        }

        return false;
    }


    public boolean challenge(String w) {

        boolean isInDictionary = IOSearcher.search(w, files);
        if (isInDictionary) {
            exists.add(w);
            return true;
        }
        notExists.add(w);
        return false;
    }
}
