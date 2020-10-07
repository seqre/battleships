package seqre.battleships.game.cell;

//TODO: Maybe some config file / interface?

public enum CellType {
    UNKNOWN('?'),
    SHIP('#'),
    EMPTY('.'),
    MISS('~'),
    HIT('@');

    final Character charRepresentation;

    CellType(Character charRepresentation) {
        this.charRepresentation = charRepresentation;
    }

    public static CellType getCellTypeFromVal(Character ch) {
        switch (ch) {
            case '#':
                return SHIP;
            case '.':
                return EMPTY;
            case '~':
                return MISS;
            case '@':
                return HIT;
            default:
                return UNKNOWN;
        }
    }

    public Character getCharRepresentation() {
        return charRepresentation;
    }
}
