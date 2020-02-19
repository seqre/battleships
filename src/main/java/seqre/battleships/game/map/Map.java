package seqre.battleships.game.map;

import seqre.battleships.game.cell.Cell;
import seqre.battleships.game.cell.CellType;
import seqre.battleships.game.ship.Ship;
import seqre.battleships.game.ship.ShipCell;
import seqre.battleships.network.Protocol;

import java.util.*;

import static seqre.battleships.game.map.Pair.constrained;

public class Map {
    private final java.util.Map<Character, ArrayList<Cell>> map;

    public Map() {
        this.map = new HashMap<>();
    }

    public Map(java.util.Map<Character, ArrayList<Cell>> map) {
        this.map = map;
    }

    public Cell getCell(Character c, int i) {
        return map.get(c).get(i);
    }

    public static Map getBlankMap() {
        Map temp = new Map();
        for (char ch = 'A'; ch <= 'J'; ch++) {
            temp.map.computeIfAbsent(ch, character -> new ArrayList<>(10));
            for (int j = 0; j < 10; j++) {
                temp.map.get(ch).add(new Cell(ch, j, CellType.UNKNOWN));
            }
        }
        return temp;
    }

    public void print() {
        map.forEach((character, bsCells) -> {
            bsCells.forEach(Cell -> System.out.print(Cell.getCellType().getCharRepresentation() + " "));
            System.out.println();
        });
    }

    public void setCell(Protocol protocol, Character ch, int i) {
        Cell cell = map.get(ch).get(i);

        switch (protocol) {
            case LAST:
            case HIT:
            case DROWNED:
                cell.setCellType(CellType.HIT);
                break;
            case MISS:
                cell.setCellType(CellType.MISS);
                break;
        }
    }

    public void cover(List<Ship> ships) {
        ships.stream()
                .filter(Ship::isDestroyed)
                .map(Ship::getShipCells)
                .flatMap(Collection::stream)
                .forEach(this::changeToEmpty);
    }

    public void uncover() {
        map.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(Cell -> Cell.getCellType() == CellType.UNKNOWN)
                .forEach(Cell -> Cell.setCellType(CellType.EMPTY));
    }

    private void changeToEmpty(ShipCell shipCell) {
        Stack<Pair> stack = new Stack<>();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i != 0 || j != 0) {
                    stack.add(new Pair((char) (shipCell.getCell().getX() + i), shipCell.getCell().getY() + j));
                }
            }
        }

        Pair tempPair;
        while (!stack.empty()) {
            tempPair = stack.pop();
            if (constrained(tempPair.getX(), tempPair.getY()) && shipCell.getCell().getCellType() == CellType.UNKNOWN) {
                map.get(tempPair.getX()).get(tempPair.getY()).setCellType(CellType.EMPTY);
            }
        }
    }
}
