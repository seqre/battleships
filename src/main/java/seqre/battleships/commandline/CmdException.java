package seqre.battleships.commandline;

import seqre.battleships.Exception;

public class CmdException extends Exception {
    public CmdException() {
        super();
    }

    public CmdException(String message) {
        super(message);
    }

    public CmdException(String message, Throwable cause) {
        super(message, cause);
    }

    public CmdException(Throwable cause) {
        super(cause);
    }
}
