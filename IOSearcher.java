package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) {
        for (String f : fileNames) {
            File file = new File(f);
            Scanner sc = null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (sc.hasNext()) {
                String line1 = sc.nextLine();
                String[] Splitted = line1.split(" ");
                for (String w : Splitted) {
                    if (w.equals(word)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}