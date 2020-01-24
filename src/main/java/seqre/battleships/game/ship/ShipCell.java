package seqre.battleships.game.ship;

import seqre.battleships.game.cell.Cell;
import seqre.battleships.game.cell.CellType;

public class ShipCell extends Cell {
    private Ship ship;

    public ShipCell(Character x, int y, Ship ship) {
        super(x, y, CellType.SHIP);
        this.ship = ship;
    }

    public void destroy() {
        cellType = CellType.HIT;
        ship.checkStatus();
    }

    public Ship getShip() {
        return ship;
    }
}
