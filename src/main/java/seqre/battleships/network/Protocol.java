package seqre.battleships.network;

public enum Protocol {
    START("start"),
    MISS("pudło"),
    HIT("trafiony"),
    DROWNED("trafiony zatopiony"),
    LAST("ostatni zatopiony");

    private String protocolText;

    Protocol(String protocolText) {
        this.protocolText = protocolText;
    }

    public String getProtocolText() {
        return protocolText;
    }

    public static Protocol getFromVal(String text) {
        switch (text) {
            case "start":
                return START;
            case "pudło":
                return MISS;
            case "trafiony":
                return HIT;
            case "trafiony zatopiony":
                return DROWNED;
            case "ostatni zatopiony":
                return LAST;
            default:
                return null;
        }
    }
}
