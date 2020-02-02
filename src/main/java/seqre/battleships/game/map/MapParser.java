package seqre.battleships.game.map;

import seqre.battleships.game.cell.Cell;
import seqre.battleships.game.cell.CellType;
import seqre.battleships.game.ship.Ship;
import seqre.battleships.game.ship.ShipCell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MapParser {
    private java.util.Map<Character, ArrayList<Cell>> map;
    private java.util.Map<Character, ArrayList<CellType>> enumMap;
    private List<Pair> coords;
    private List<Ship> ships;
    private final Scanner in;

    public MapParser(File mapFile) throws ParserBSException {
        this.map = null;
        this.ships = null;
        this.coords = null;
        try {
            in = new Scanner(new BufferedReader(new FileReader(mapFile)));
        } catch (FileNotFoundException e) {
            throw new ParserBSException("Cannot open map file", e);
        }
    }

    public Map getMap() {
        if (map == null) {
            parseMapFile();
        }
        return new Map(map);
    }

    public List<Ship> getShips() {
        if (ships == null) {
            parseMapFile();
        }
        return ships;
    }

    private void parseMapFile() {
        map = new HashMap<>();
        ships = new ArrayList<>();
        enumMap = new HashMap<>();
        coords = new LinkedList<>();

        String line;

        for (int i = 0; i < 10; i++) {
            line = in.nextLine();
            int finalI = i;
            line.chars().forEach(value -> addToEnumMap((char) (65 + finalI), value));
            for (int j = 0; j < 10; j++) {
                coords.add(new Pair((char) (65 + i), j));
            }
        }

        int n = coords.size();
        while (n > 0) {
            findShips(coords.get(0));
            n = coords.size();
        }
    }

    private void addToEnumMap(Character character, int i) {
        Character ch = (char) i;
        CellType cellType = CellType.getCellTypeFromVal(ch);
        enumMap.computeIfAbsent(character, ArrayList::new).add(cellType);
    }

    private void findShips(Pair pair) {
        if (enumMap.get(pair.x).get(pair.y) == CellType.SHIP) {
            List<Pair> visited = new ArrayList<>();
            Stack<Pair> toVisit = new Stack<>();
            Ship tempShip = new Ship();
            ShipCell tempCell;
            Pair tempPair;
            Pair tempPair2;

            toVisit.add(pair);
            while (!toVisit.empty()) {
                tempPair = toVisit.pop();
                tempCell = new ShipCell(tempPair.x, tempPair.y, tempShip);

                map.computeIfAbsent(tempPair.x, character -> new ArrayList<>(10)).add(Math.min(tempPair.y, map.get(tempPair.x).size()), tempCell);
                tempShip.addCell(tempCell);

                if (Pair.constrained((char) (tempPair.x + 1), tempPair.y) && enumMap.get((char) (tempPair.x + 1)).get(tempPair.y) == CellType.SHIP) {
                    tempPair2 = new Pair((char) (tempPair.x + 1), tempPair.y);
                    if (!visited.contains(tempPair2)) toVisit.add(tempPair2);
                }
                if (Pair.constrained((char) (tempPair.x - 1), tempPair.y) && enumMap.get((char) (tempPair.x - 1)).get(tempPair.y) == CellType.SHIP) {
                    tempPair2 = new Pair((char) (tempPair.x - 1), tempPair.y);
                    if (!visited.contains(tempPair2)) toVisit.add(tempPair2);
                }
                if (Pair.constrained(tempPair.x, tempPair.y - 1) && enumMap.get(tempPair.x).get(tempPair.y - 1) == CellType.SHIP) {
                    tempPair2 = new Pair(tempPair.x, tempPair.y - 1);
                    if (!visited.contains(tempPair2)) toVisit.add(tempPair2);
                }
                if (Pair.constrained(tempPair.x, tempPair.y + 1) && enumMap.get(tempPair.x).get(tempPair.y + 1) == CellType.SHIP) {
                    tempPair2 = new Pair(tempPair.x, tempPair.y + 1);
                    if (!visited.contains(tempPair2)) toVisit.add(tempPair2);
                }

                visited.add(tempPair);
            }

            visited.forEach(coords::remove);
            tempShip.setShipType();
            ships.add(tempShip);
        } else {
            map.computeIfAbsent(pair.x, ArrayList::new).add(Math.min(pair.y, map.get(pair.x).size()), new Cell(pair.x, pair.y, CellType.EMPTY));
            coords.remove(pair);
        }
    }

    private static class Pair {
        Character x;
        int y;

        public Pair(Character i, int j) {
            this.x = i;
            this.y = j;
        }

        public static boolean constrained(Character x, int y) {
            return 'A' <= x && x <= 'J' && 0 <= y && y < 10;
        }

        @Override
        public String toString() {
            return "[" + x.toString() + "," + y + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                Pair temp = (Pair) obj;
                return x == temp.x && y == temp.y;
            } else return false;
        }
    }
}
