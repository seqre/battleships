package seqre.battleships.game.ship;

public enum ShipType {
    SMALL(1),
    MEDIUM(2),
    BIG(3),
    VAST(4);

    private final int size;

    ShipType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static ShipType getTypeFromSize(int size) {
        switch (size) {
            case 1:
                return SMALL;
            case 2:
                return MEDIUM;
            case 3:
                return BIG;
            case 4:
                return VAST;
            default:
                return null;
        }
    }
}
