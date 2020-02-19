package seqre.battleships.network;

import seqre.battleships.game.GameState;
import seqre.battleships.game.GameStatus;
import seqre.battleships.game.map.MapParser;
import seqre.battleships.game.map.ParserBSException;

import java.io.*;
import java.net.Socket;

public class Session implements Runnable {
    private final InstanceType mode;
    private final Socket socket;
    private final BufferedWriter out;
    private final BufferedReader in;
    private GameState gameState;
    private Character lastX;
    private int lastY;

    public Session(Socket socket, InstanceType mode, File mapFile) throws IOException, ParserBSException {
        this.mode = mode;
        this.socket = socket;
        this.gameState = new GameState(new MapParser(mapFile));
        out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public void run() {
        System.out.println("Session up&running");
        Protocol result;
        try {
            if (mode == InstanceType.CLIENT) {
                lastX = gameState.getNextChar();
                lastY = gameState.getNextInt();
                out.write("start;" + lastX + lastY);
                System.out.println("start;" + lastX + lastY);
                out.newLine();
                out.flush();
                gameState.setGameStatus(GameStatus.ONGOING);
            } else {
                send(receive(in.readLine()));
            }
        } catch (IOException e) {
            (new NetBSException("Cannot initiate conversation", e)).printStackTrace();
            System.exit(-1);
        }

        try {
            while (gameState.getGameStatus() == GameStatus.ONGOING) {
                result = receive(in.readLine());
                send(result);
                if (result == Protocol.LAST) {
                    gameState.endGame(GameStatus.FAIL);
                }
            }
        } catch (IOException e) {
            (new NetBSException("Error during ongoing game", e)).printStackTrace();
        }
    }

    private void send(Protocol protocol) throws IOException {
        lastX = gameState.getNextChar();
        lastY = gameState.getNextInt();
        if (protocol == Protocol.LAST) {
            out.write(protocol.getProtocolText());
            System.out.println(mode.toString() + " sent: " + protocol.getProtocolText());
        } else {
            out.write(protocol.getProtocolText() + ";" + lastX + lastY);
            System.out.println(mode.toString() + " sent: " + protocol.getProtocolText() + ";" + lastX + lastY);
        }
        out.newLine();
        out.flush();
    }

    private Protocol receive(String data) {
        System.out.println(mode.toString() + " got: " + data);
        String[] args = data.stripTrailing().split(";");

        if (gameState.getGameStatus() == GameStatus.STARTING) {
            lastX = args[1].charAt(0);
            lastY = Integer.parseInt(args[1].substring(1));
            gameState.setGameStatus(GameStatus.ONGOING);
        } else {
            Protocol result = Protocol.getFromVal(args[0]);
            gameState.saveResult(result, lastX, lastY - 1);
        }

        return gameState.checkAndSet(args[1].charAt(0), Integer.parseInt(args[1].substring(1)));
    }
}
