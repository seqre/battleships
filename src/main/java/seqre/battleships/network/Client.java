package seqre.battleships.network;

import seqre.battleships.game.map.ParserBSException;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Instance {
    private final InetAddress inetAddress;
    private final int port;
    private Session session;
    private Thread innerThread;

    public Client(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
    }

    @Override
    public void play(File mapFile) {
        try {
            System.out.println("Client started");
            Socket socket = new Socket("10.136.98.112", port);
            session = new Session(socket, InstanceType.CLIENT, mapFile);
            innerThread = new Thread(session, "bsClient");
            innerThread.start();
        } catch (ParserBSException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            (new NetBSException("Connectivity error", e)).printStackTrace();
            System.exit(1);
        }
    }
}
