package seqre.battleships.game;

import seqre.battleships.game.cell.Cell;
import seqre.battleships.game.cell.CellType;
import seqre.battleships.game.map.Map;
import seqre.battleships.game.map.MapParser;
import seqre.battleships.game.ship.Ship;
import seqre.battleships.game.ship.ShipCell;
import seqre.battleships.network.Protocol;

import java.util.List;
import java.util.Random;

public class GameState {
    private Map playerMap;
    private Map enemyMap;
    private List<Ship> ships;
    private GameStatus gameStatus;
    private final Random random;

    public GameState(MapParser mapParser) {
        this.playerMap = mapParser.getMap();
        this.ships = mapParser.getShips();
        this.enemyMap = Map.getBlankMap();
        this.gameStatus = GameStatus.STARTING;
        this.random = new Random();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void endGame(GameStatus gameStatus) {
        if (gameStatus == GameStatus.WIN) {
            System.out.println("Wygrana");
            enemyMap.uncover();
        } else {
            System.out.println("Przegrana");
            enemyMap.cover(ships);
        }
        System.out.println("Mapa przeciwnika");
        enemyMap.print();
        System.out.println("\nTwoja mapa");
        playerMap.print();
        System.exit(0);
    }

    public void printMaps() {
        playerMap.print();
        System.out.println();
        enemyMap.print();
    }

    public Character getNextChar() {
        return (char) ('A' + random.nextInt(10));
    }

    public int getNextInt() {
        return random.nextInt(10) + 1;
    }

    public Protocol checkAndSet(Character ch, int i) {
        i = i - 1;
        Cell cell = playerMap.getCell(ch, i);

        if (cell.getShipCell() != null) {
            ShipCell shipCell = cell.getShipCell();
            shipCell.destroy();

            if (checkShips()) {
                return Protocol.LAST;
            }

            if (shipCell.getShip().isDestroyed()) return Protocol.DROWNED;
            else return Protocol.HIT;

        } else {
            cell.setCellType(CellType.MISS);
            return Protocol.MISS;
        }
    }

    private boolean checkShips() {
        return ships.stream().allMatch(Ship::isDestroyed);
    }

    public void saveResult(Protocol protocol, Character ch, int i) {
        if (protocol == Protocol.LAST) endGame(GameStatus.WIN);
        enemyMap.setCell(protocol, ch, i);
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void printGameState() {
        ships.forEach(Ship -> System.out.print(Ship.isDestroyed() + " "));
        System.out.println(checkShips());
    }
}
