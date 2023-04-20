package test;


import java.util.Objects;
import java.util.Random;

public class Tile
{
    final public char letter;
    final public int score;
    final public static int[] Tscore = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(letter, score);
    }

    public Tile(char letter, int score)
    {
        this.letter = letter;
        this.score = score;
    }

    public char getLetter()
    {
        return letter;
    }

    public int getScore()
    {
        return score;
    }

    static public class Bag
    {
        // T=Tile, B=Bag.
        private int[] Tquantity = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        private final Tile[] Ttype;
        private static Bag b = null;
        public static int Bsize = 98;

        private Bag()
        {
            char l = 'A';
            Ttype = new Tile[26];
            for (int i = 0; i < 26; i++)
            {
                Ttype[i] = new Tile(l, Tscore[i]);
                l++;
            }
        }

        public static Bag getBag()
        {
            if (b == null) {
                b = new Bag();
            }
            return b;
        }

        public int[] getQuantities()
        {
            int[] copy = new int[26];
            System.arraycopy(this.Tquantity, 0, copy, 0, this.Tquantity.length);
            return copy;
        }

        public int size()
        {
            return Bsize;
        }

        public Tile getTile(char p)
        {
            int a = p - 65;
            if (a < 0 || a > 25)
            {
                return null;
            }
            return this.Tquantity[a] > 0 ? Ttype[a] : null;
        }

        public Tile getRand()
        {
            if (Bsize > 0)
            {
                Random p = new Random(); // p = Parameter
                int i = p.nextInt(26);
                if (this.Tquantity[i] == 0)
                {
                    while (this.Tquantity[i % 25] == 0)
                    {
                        i++;
                    }
                }
                char pc = Ttype[i].getLetter();
                this.Tquantity[i]--;
                Bsize--;
                return new Tile(pc, this.Ttype[i].getScore());
            }
            return null;
        }

        public void put(Tile t)
        {
            int[] copy = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            int a = t.letter - 65;
            if (this.Tquantity[a] < copy[a])
            {
                this.Tquantity[a]++;
                Bsize++;
            }
        }
    }
}
