package seqre.battleships.commandline;

import seqre.battleships.BSException;

public class CmdBSException extends BSException {
    public CmdBSException() {
        super();
    }

    public CmdBSException(String message) {
        super(message);
    }

    public CmdBSException(String message, Throwable cause) {
        super(message, cause);
    }

    public CmdBSException(Throwable cause) {
        super(cause);
    }
}
