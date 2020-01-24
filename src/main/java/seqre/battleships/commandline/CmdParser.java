package seqre.battleships.commandline;

import seqre.battleships.network.InstanceType;

import java.nio.file.Path;

public class CmdParser {
    String[] args;
    InstanceType instanceType;
    Integer port;
    Path mapPath;

    public CmdParser(String[] args) {
        this.args = args;
    }

    public void parse() throws CmdException {
        if (args.length != 6) {
            System.out.println("Proper usage: java BattleShips-1.0.jar -mode [server|client] -port N -map map-file");
            throw new CmdException("Too less/many arguments");
        }

        for (int it = 0; it < args.length; it++) {
            try {
                switch (args[it]) {
                    case "-mode":
                        instanceType = InstanceType.valueOf((args[++it]).toUpperCase());
                        break;

                    case "-port":
                        port = Integer.valueOf(args[++it]);
                        break;

                    case "-map":
                        mapPath = Path.of(args[++it]);
                        break;
                }
            } catch (IllegalArgumentException e) {
                throw new CmdException("Wrong arguments", e);
            }
        }
    }

    public InstanceType getInstanceType() {
        return instanceType;
    }

    public int getPort() {
        return port;
    }

    public Path getMapPath() {
        return mapPath;
    }
}
