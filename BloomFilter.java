package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Objects;

public class BloomFilter {

    final private BitSet bs;
    ArrayList<MessageDigest> argsList;

    public BloomFilter(int i, String... args) {
        bs = new BitSet(i);
        argsList = new ArrayList<>();
        for (String s : args) {
            try {
                argsList.add(MessageDigest.getInstance(s));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException();
            }
        }
    }

    public void add(String word) {
        for (MessageDigest m : this.argsList) {
            BigInteger bi = new BigInteger(m.digest(word.getBytes()));
            this.bs.set(Math.abs(bi.intValue()) %  this.bs.size(), true);
        }
    }

    public boolean contains (String word){
        for (MessageDigest m : this.argsList) {
            BigInteger bi = new BigInteger(m.digest(word.getBytes()));
            if (!this.bs.get((Math.abs(bi.intValue()) %  this.bs.size()))){
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object obj) {
       if (this == obj) return true;
       if(obj == null || getClass() != obj.getClass()) return false;
       BloomFilter temp = (BloomFilter) obj;
       return Objects.equals(bs, temp.bs) && Objects.deepEquals(argsList, temp.argsList);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.bs.length(); i++) {
            s.append(this.bs.get(i) ? 1 : 0);
        }
        return s.toString();
    }
}
