package seqre.battleships.game.ship;

import seqre.battleships.game.cell.CellType;

import java.util.ArrayList;

public class Ship {
    private ShipType shipType;
    private ShipStatus shipStatus;
    private final ArrayList<ShipCell> shipCells;

    public Ship() {
        this.shipType = null;
        this.shipStatus = ShipStatus.INTACT;
        this.shipCells = new ArrayList<>(4);
    }

    public void setShipType() {
        shipCells.trimToSize();
        this.shipType = ShipType.getTypeFromSize(shipCells.size());
    }

    public void addCell(ShipCell cell) {
        shipCells.add(cell);
    }

    public void printCells() {
        System.out.println(shipCells.toString());
    }

    public boolean isDestroyed() {
        return shipStatus == ShipStatus.DESTROYED;
    }

    public void checkStatus() {
        long count = shipCells.stream().filter(cell -> cell.getCell().getCellType() == CellType.HIT).count();

        if (count == shipType.getSize()) {
            shipStatus = ShipStatus.DESTROYED;
        } else if (count > 0) {
            shipStatus = ShipStatus.DAMAGED;
        }
    }

    public ArrayList<ShipCell> getShipCells() {
        return shipCells;
    }

    public ShipType getShipType() {
        return shipType;
    }
}
