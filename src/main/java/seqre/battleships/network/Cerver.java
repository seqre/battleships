package seqre.battleships.network;

import seqre.battleships.game.map.ParserException;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cerver implements Instance {
    private final ServerSocket serverSocket;
    private Session session;
    private Thread innerThread;

    public Cerver(InetAddress inetAddress, int port) throws IOException {
        this.serverSocket = new ServerSocket(port, 1, inetAddress);
    }

    @Override
    public void play(File mapFile) {
        try {
            System.out.println("Server started on: " + serverSocket.getInetAddress().getHostAddress() + " port: " + serverSocket.getLocalPort());
            Socket socket = serverSocket.accept();
            session = new Session(socket, InstanceType.SERVER, mapFile);
            innerThread = new Thread(session, "bsServer");
            innerThread.start();
        } catch (ParserException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            (new NetException("Connectivity error", e)).printStackTrace();
            System.exit(1);
        }
    }
}
