package seqre.battleships.game.map;

import seqre.battleships.game.cell.Cell;
import seqre.battleships.game.cell.CellType;
import seqre.battleships.game.ship.Ship;
import seqre.battleships.game.ship.ShipCell;
import seqre.battleships.game.ship.ShipType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

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

        while (coords.size() > 0) {
            findShips(coords.get(0));
        }

        if (!checkShips()) {
            System.out.println("Wrongly positioned ships");
            System.exit(-1);
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
            Stack<Pair> toInternalVisit = new Stack<>();
            Ship tempShip = new Ship();
            ShipCell tempCell;
            Pair tempPair;
            Pair tempPairInternal;

            toVisit.add(pair);
            while (!toVisit.empty()) {
                tempPair = toVisit.pop();
                tempCell = new ShipCell(tempPair.x, tempPair.y, tempShip);

                map.computeIfAbsent(tempPair.x, character -> new ArrayList<>(10)).add(Math.min(tempPair.y, map.get(tempPair.x).size()), tempCell);
                tempShip.addCell(tempCell);

                toInternalVisit.add(new Pair((char) (tempPair.x + 1), tempPair.y));
                toInternalVisit.add(new Pair((char) (tempPair.x - 1), tempPair.y));
                toInternalVisit.add(new Pair(tempPair.x, tempPair.y + 1));
                toInternalVisit.add(new Pair(tempPair.x, tempPair.y - 1));

                while (!toInternalVisit.empty()) {
                    tempPairInternal = toInternalVisit.pop();
                    try {
                        if (enumMap.get(tempPairInternal.x).get(tempPairInternal.y) == CellType.SHIP) {
                            if (!visited.contains(tempPairInternal)) toVisit.add(tempPairInternal);
                        }
                    } catch (Exception ignored) {
                    }
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

    private boolean checkShips() {
        java.util.Map<ShipType, Long> counter = ships.stream()
                .collect(Collectors.groupingBy(Ship::getShipType, Collectors.counting()));

        return  counter.get(ShipType.SMALL) == 4 &&
                counter.get(ShipType.MEDIUM) == 3 &&
                counter.get(ShipType.BIG) == 2 &&
                counter.get(ShipType.VAST) == 1;
    }
}
