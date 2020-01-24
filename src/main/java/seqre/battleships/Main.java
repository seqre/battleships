package seqre.battleships;

import seqre.battleships.commandline.CmdException;
import seqre.battleships.commandline.CmdParser;
import seqre.battleships.network.Instance;
import seqre.battleships.network.NetException;

import java.io.File;

import static seqre.battleships.network.NetUtils.findAddress;


public class Main {
    static private Instance instance;

    public static void main(String[] args) {
        CmdParser cmdParser = new CmdParser(args);
        createInstance(cmdParser);
        File mapFile = getMapFile(cmdParser);
        instance.play(mapFile);
    }

    private static void createInstance(CmdParser cmdParser) {
        try {
            cmdParser.parse();
            instance = Instance.getInstance(cmdParser.getInstanceType(), findAddress(), cmdParser.getPort());
        } catch (CmdException | NetException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static File getMapFile(CmdParser cmdParser) {
        return cmdParser.getMapPath().toFile();
    }
}
