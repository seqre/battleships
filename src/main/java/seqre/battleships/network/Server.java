package seqre.battleships.network;

import seqre.battleships.game.map.ParserBSException;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Instance {
    private final ServerSocket serverSocket;
    private Session session;
    private Thread innerThread;

    public Server(InetAddress inetAddress, int port) throws IOException {
        this.serverSocket = new ServerSocket(port, 1, inetAddress);
    }

    //TODO: Use proper logging

    @Override
    public void play(File mapFile) {
        try {
            System.out.println("Server started on: " + serverSocket.getInetAddress().getHostAddress() + " port: " + serverSocket.getLocalPort());
            Socket socket = serverSocket.accept();
            session = new Session(socket, InstanceType.SERVER, mapFile);
            innerThread = new Thread(session, "bsServer");
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
