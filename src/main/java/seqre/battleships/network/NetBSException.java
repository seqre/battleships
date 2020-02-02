package seqre.battleships.network;

import seqre.battleships.BSException;

public class NetBSException extends BSException {
    public NetBSException() {
        super();
    }

    public NetBSException(String message) {
        super(message);
    }

    public NetBSException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetBSException(Throwable cause) {
        super(cause);
    }
}
