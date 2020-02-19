package seqre.battleships.game.ship;

import seqre.battleships.game.cell.Cell;
import seqre.battleships.game.cell.CellType;

public class ShipCell {
    private Ship ship;
    private Cell cell;

    public ShipCell(Ship ship, Cell cell) {
        this.ship = ship;
        this.cell = cell;
    }

    public void destroy() {
        cell.setCellType(CellType.HIT);
        ship.checkStatus();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Ship getShip() {
        return ship;
    }
}
