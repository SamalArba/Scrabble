package test;

public class Word
{
    private final Tile[] tiles;
    private final int row;
    private final int col;
    private final boolean vertical;

    public Word(Tile[] tiles, int row, int col, boolean vertical)
    {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }

    public boolean isVertical()
    {
        return vertical;
    }

    public Tile[] getTiles()
    {
        return this.tiles;
    }
}
