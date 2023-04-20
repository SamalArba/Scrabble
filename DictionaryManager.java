package test;


import java.util.HashMap;

public class DictionaryManager {
    private HashMap<String, Dictionary> bookMap;
    private static DictionaryManager DM = null;

    private DictionaryManager(){
        bookMap = new HashMap<>();
    }

    public boolean challenge(String... args) {
        String searchTerm = args[args.length-1];
        boolean found = false;
        for (int i = 0; i < args.length - 1; i++) {
            String book = args[i];
            if (!bookMap.containsKey(book)) {
                bookMap.put(book, new Dictionary(book));
            }
            found = found || bookMap.get(book).challenge(searchTerm);
        }
        return found;
    }


    public boolean query(String... args) {
        String searchTerm = args[args.length - 1];
        boolean found = false;
        for (int i = 0; i < args.length - 1; i++) {
            String book = args[i];
            if (!bookMap.containsKey(book)) {
                bookMap.put(book, new Dictionary(book));
            }
            found = found || bookMap.get(book).query(searchTerm);
        }
        return found;
    }

    public int getSize(){
        return bookMap.size();
    }

    public static DictionaryManager get(){
        if(DM == null){
            DM = new DictionaryManager();
        }
        return DM;
    }

}
