package seqre.battleships;

public class BSException extends java.lang.Exception {
    public BSException() {
        super();
    }

    public BSException(String message) {
        super(message);
    }

    public BSException(String message, Throwable cause) {
        super(message, cause);
    }

    public BSException(Throwable cause) {
        super(cause);
    }
}
