package seqre.battleships.game.cell;

public class Cell {
    private Character x;
    private int y;
    protected CellType cellType;

    public Cell() {
        this.cellType = CellType.UNKNOWN;
    }

    public Cell(Character x, int y, CellType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public Character getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
