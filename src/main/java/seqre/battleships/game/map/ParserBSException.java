package seqre.battleships.game.map;

import seqre.battleships.BSException;

public class ParserBSException extends BSException {
    public ParserBSException() {
        super();
    }

    public ParserBSException(String message) {
        super(message);
    }

    public ParserBSException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserBSException(Throwable cause) {
        super(cause);
    }
}
