package seqre.battleships.game.cell;

import seqre.battleships.game.ship.ShipCell;

public class Cell {
    private final Character x;
    private final int y;
    protected CellType cellType;
    private ShipCell shipCell;

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

    public ShipCell getShipCell() {
        return shipCell;
    }

    public void setShipCell(ShipCell shipCell) {
        this.shipCell = shipCell;
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
