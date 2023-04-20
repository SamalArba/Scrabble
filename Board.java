package test;


import java.util.ArrayList;

public class Board {
    private static Board b = null;
    private Square[][] board;

    public static Board getBoard() {
        if (b == null) {
            b = new Board();
        }
        return b;
    }

    private Board() {                              // Making the board.
        this.board = new Square[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                this.board[i][j] = new Square();
            }
        }
        for (int i = 0; i < 15; i += 7) {           // Applying Bonus Word 3
            for (int j = 0; j < 15; j += 7) {
                if (i != 7 && j != 7) {
                    this.board[i][j].setBonus(Bonus.WX3);
                }
            }
        }
        for (int i = 1; i < 5; i++) {                // Applying Bonus Word 2
            this.board[i][i].setBonus(Bonus.WX2);
            this.board[14 - i][i].setBonus(Bonus.WX2);
            this.board[i][9 + i].setBonus(Bonus.WX2);
            this.board[9 + i][9 + i].setBonus(Bonus.WX2);
        }
        for (int i = 1; i < 14; i += 4) {                // Applying Bonus Tile 3
            for (int j = 5; j < 10; j += 4) {
                this.board[i][j].setBonus(Bonus.TX3);
                this.board[j][i].setBonus(Bonus.TX3);
            }
        }
        for (int i = 3; i < 12; i += 8) {               // Applying Bonus Tile 2
            for (int j = 0; j < 15; j += 14) {
                this.board[i][j].setBonus(Bonus.TX2);
                this.board[j][i].setBonus(Bonus.TX2);
            }
        }
        for (int i = 2; i < 7; i += 4) {
            for (int j = 6; j < 9; j += 2) {
                this.board[i][j].setBonus(Bonus.TX2);
                this.board[14-i][14-j].setBonus(Bonus.TX2);
            }
        }
        for(int i=6; i<8; i+=2){
            for (int j=2; j<13; j+=10) {
               this.board[i][j].setBonus(Bonus.TX2);
            }
        }
        this.board[7][3].setBonus(Bonus.TX2);
        this.board[7][11].setBonus(Bonus.TX2);
        this.board[3][7].setBonus(Bonus.TX2);
        this.board[11][7].setBonus(Bonus.TX2);
        this.board[7][7].setBonus(Bonus.STAR);
    }

    public ArrayList<Word> getWords(Word w) {
        int row = w.getRow(), col = w.getCol(), len = w.getTiles().length, j, t;
        ArrayList<Word> list = new ArrayList<>();
        list.add(w);                                                  // Adding the word itself.
        if (w.isVertical()) {                                         // Checking if there are words above/below w.
            if (b.board[row - 1][col].getTile() != null) {
                if (b.board[row + len][col].getTile() != null) {
                    list.add(b.makeANewWord(row - 1, col, len + 2, true));
                } else {
                    list.add(b.makeANewWord(row - 1, col, len + 1, true));
                }
            } else if (b.board[row - 1][col].getTile() == null && b.board[row + len][col].getTile() != null) {
                list.add(b.makeANewWord(row, col, len + 1, true));
            }
            for (int i = 0; i < len; i++) {                          // checking if there are words right/left to w or
                if (w.getTiles()[i] != null) {                       // if they go through it.
                    j = col - 1;
                    t = col + 1;
                    if ((b.board[row + i][col + 1].getTile() != null) && (b.board[row + i][col - 1].getTile() != null)) {
                        while (b.board[row + i][j].getTile() != null) {
                            j--;
                        }
                        while (b.board[row + i][t].getTile() != null) {
                            t++;
                        }
                        list.add(b.makeANewWord(row + i, j + 1, t - j - 1, false));
                    }
                    if (b.board[row + i][col + 1].getTile() != null) {
                        while (b.board[row + i][t].getTile() != null) {
                            t++;
                        }
                        list.add(b.makeANewWord(row + i, col, t - col, false));
                    }
                    if (b.board[row + i][col - 1].getTile() != null) {
                        while (b.board[row + i][j].getTile() != null) {
                            j--;
                        }
                        list.add(b.makeANewWord(row + i, j + 1, col - j, false));
                    }
                }
            }
        } else {
            if (b.board[row][col - 1].getTile() != null) {            // checking if there are words right/left to w
                if (b.board[row][col + len].getTile() != null && col + len < 15) {
                    list.add(b.makeANewWord(row, col - 1, len + 2, false));
                } else {
                    list.add(b.makeANewWord(row, col - 1, len + 1, false));
                }
            } else if (b.board[row][col - 1].getTile() == null && b.board[row][col + len].getTile() != null) {
                list.add(b.makeANewWord(row, col, len + 1, false));
            }
            for (int i = 0; i < len; i++)                                //checking if there are words above/below w.
            {                                                            // if they go through it.
                if (w.getTiles()[i] != null) {
                    j = row - 1;
                    t = row + 1;
                    if ((b.board[row + 1][col + i].getTile() != null) && (b.board[row - 1][col + i].getTile() != null)) {
                        while (b.board[j][col + i].getTile() != null) {
                            j--;
                        }
                        while (b.board[t][col + i].getTile() != null) {
                            t++;
                        }
                        list.add(b.makeANewWord(j + 1, col + i, t - j - 1, true));
                    }
                    else if (b.board[row + 1][col + i].getTile() != null) {
                        while (b.board[t][col + i].getTile() != null) {
                            t++;
                        }
                        list.add(b.makeANewWord(row, col + i, t - row, true));
                    }
                    else if (b.board[row - 1][col + i].getTile() != null) {
                        while (b.board[j][col + i].getTile() != null) {
                            j--;
                        }
                        list.add(b.makeANewWord(j + 1, col + i, row - j, true));
                    }
                }
            }
        }
        return list;
    }

    public Word makeANewWord(int row, int col, int len, boolean vertical)
    {
        Tile[] tiles;
        if (vertical)
        {
            tiles = new Tile[len];
            for (int i = 0; i < len; i++)
            {
                tiles[i] = new Tile(b.board[row + i][col].getTile().getLetter(), b.board[row + i][col].getTile().getScore());
            }
            return new Word(tiles, row, col, true);
        }
        else
        {
            tiles = new Tile[len];
            for (int i = 0; i < len; i++)
            {
                tiles[i] = new Tile(b.board[row][col + i].getTile().getLetter(), b.board[row][col + i].getTile().getScore());
            }
            return new Word(tiles, row, col, false);
        }
    }

    public int getScore(Word w) {
        int[] Multipliers = {1, 0}; // First int is Word multiplier and second int is Tile multiplier.
        int score = 0;
        int len = w.getTiles().length;
        if (len <= 1) {
            return 0;
        }
        if (w.isVertical()) {
            for (int i = w.getRow(); i < w.getRow() + len; i++)
            {
                score += b.board[i][w.getCol()].getTile().getScore();
                Multipliers = applyBonus(b.board[i][w.getCol()], Multipliers);
            }
        }
        else
        {
            for (int i = w.getCol(); i < w.getCol() + len; i++)
            {
                score += b.board[w.getRow()][i].getTile().getScore();
                Multipliers = applyBonus(b.board[w.getRow()][i], Multipliers);
            }
        }
        return (score + Multipliers[1]) * Multipliers[0];
    }

    public int[] applyBonus(Square s, int[] multipliers) {
        if (s.getBonus() == Bonus.TX1) {
        return multipliers;
        }  else if (s.getBonus() == Bonus.TX2) {
            multipliers[1] += s.getTile().getScore();
        } else if (s.getBonus() == Bonus.TX3) {
            multipliers[1] += s.getTile().getScore() * 2;
        } else if (s.getBonus() == Bonus.WX2) {
            multipliers[0] *= 2;
        } else if (s.getBonus() == Bonus.WX3) {
            multipliers[0] *= 3;
        } else if (s.getBonus() == Bonus.STAR) {
            multipliers[0] *= 2;
            s.setBonus(Bonus.TX1);
            }
        return multipliers;
    }

    public Square[][] getTiles() {
        int up = 15, down = 0, left = 15, right = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.board[i][j].getTile() != null) {
                    if (j < left) {
                        left = j;
                    }
                    if (j > right) {
                        right = j;
                    }
                    if (i < up) {
                        up = i;
                    }
                    if (i > down) {
                        down = i;
                    }
                }
            }
        }
        Square[][] copy = new Square[down-up+1][right-left+1];
        for (int i = 0; i < down-up+1; i++) {
            for (int j = 0; j < right-left+1; j++) {
                copy[i][j] = new Square();
                copy[i][j] = this.board[up+i][left+j];
            }
        }
        return copy;
    }

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    public boolean boardLegal(Word w) {                            // checking if the word is first
        if (b.isFirst()) {                                         // checking if vertical
            if (w.isVertical()) {                                  // checking if the word is out of bounds
                if (w.getCol() == 7 && w.getRow() <= 7) {
                    if (w.getRow() + w.getTiles().length >= 7) {
                        return b.isWordInsideBorders(w);
                    }
                }
            } else {
                if (w.getCol() <= 7 && w.getRow() == 7) {
                    if (w.getCol() + w.getTiles().length >= 7) {
                        return b.isWordInsideBorders(w);
                    }
                }
            }
        } else {
            if (b.isWordInsideBorders(w)) {
                return b.isWordAdjacent(w);
            }
        }
        return false;
    }

    public int tryPlaceWord(Word w)                                        // placing the tiles of the words in order
    {                                                                      // for the other checkups to work.
        applyWord(w);
        int totalScore = 0;
        if (b.boardLegal(w)) {
            ArrayList<Word> list = getWords(w);
            if (list.size() > 0) {
                for (Word word : list) {
                    if (!b.dictionaryLegal(word)) {
                        return reApplyWord(w);
                    }
                    if (b.board[word.getRow()][word.getCol()].getTile() != null) {
                        totalScore += b.getScore(word);
                    }
                }
            }
            return totalScore;
        }
        return reApplyWord(w);
    }

    private void applyWord(Word w) {
        if (w.isVertical()) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (b.board[w.getRow() + i][w.getCol()].getTile() == null) {
                    b.board[w.getRow() + i][w.getCol()].setTile(w.getTiles()[i]);
                }
            }
        } else {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (b.board[w.getRow()][w.getCol() + i].getTile() == null) {
                    b.board[w.getRow()][w.getCol() + i].setTile(w.getTiles()[i]);
                }
            }
        }
    }

    private int reApplyWord(Word w) {
        if (w.isVertical()) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (b.board[w.getRow() + i][w.getCol()] != null)
                {
                    b.board[w.getRow() + i][w.getCol()] = null;
                }
            }
        } else {
            for (int i = 0; i < w.getTiles().length; i++)
            {
                if (b.board[w.getRow()][w.getCol() + i] != null)
                {
                    b.board[w.getRow()][w.getCol() + i] = null;
                }
            }
        }
        return 0;
    }

    public boolean isWordInsideBorders(Word w) {
        if (w.isVertical()) {
            return (15 > w.getTiles().length + w.getRow()) && (w.getCol() >= 0) && (w.getCol() < 15) && (w.getRow() >= 0);
        } else {
            return (15 > w.getTiles().length + w.getCol()) && (w.getCol() >= 0) && (w.getCol() < 15) && (w.getRow() >= 0);
        }
    }

    public boolean isWordAdjacent(Word w) // adjacent to another word
    {
        for (int i = 0; i < w.getTiles().length; i++) {
            if (w.getTiles()[i] == null) {
                return true;
            }
        }
        int i, j;
        int len = w.getTiles().length;
        if (w.isVertical())
        {
            if (b.board[w.getRow() - 1][w.getCol()] == null || (w.getRow() - 1 < 0)
            ) {
                if (b.board[w.getRow() + len][w.getCol()] == null || w.getRow() + len > 15)
                {
                    for (i = w.getRow(); i < len; i++) {
                        for (j = w.getCol() - 1; j < w.getCol() + 2; j += 2)
                        {
                            if (b.board[i][j] != null)
                                return true;
                        }
                    }
                    return false;
                }
                return true;
            }
        } else
        {
            if (b.board[w.getRow()][w.getCol() - 1] == null || w.getCol() - 1 < 0)
            {
                if (b.board[w.getRow()][w.getCol() + len] == null || w.getCol() + len > 15)
                {
                    for (i = w.getRow() - 1; i < w.getRow() + 2; i += 2)
                    {
                        for (j = w.getCol(); j < w.getCol() + len; j++)
                        {
                            if (b.board[i][j] != null)
                                return true;
                        }
                    }
                    return false;
                }
                return true;
            }
        }
        return true;
    }

    public boolean isFirst()
    {
        return b.board[7][7].getTile() == null;
    }

    static public class Square
    {
        private Tile tile;
        private Bonus bonus;

        public Square() {
            this.tile = null;
            this.bonus = Bonus.TX1;
        }

        public void setTile(Tile tile) {
            this.tile = tile;
        }

        public void setBonus(Bonus bonus) {
            this.bonus = bonus;
        }

        public Tile getTile() {
            return tile;
        }

        public Bonus getBonus() {
            return bonus;
        }
    }

    public enum Bonus
    {
        // T = Tile, W = Word, X = Multiplication.
        TX1,
        TX2,
        TX3,
        WX2,
        WX3,
        STAR
    }
}

