package seqre.battleships.game.map;

public class Pair {
    private final Character x;
    private final int y;

    public Pair(Character i, int j) {
        this.x = i;
        this.y = j;
    }

    public Character getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static boolean constrained(char c, int i) {
        return 'A' <= c && c <= 'J' && 0 <= i && i <= 9;
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