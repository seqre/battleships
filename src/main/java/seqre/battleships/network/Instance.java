package seqre.battleships.network;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public interface Instance {
    static Instance getInstance(InstanceType instanceType, InetAddress inetAddress, int port) throws NetBSException {
        try {
            switch (instanceType) {
                case CLIENT:
                    return new Client(inetAddress, port);
                case SERVER:
                default:
                    return new Server(inetAddress, port);
            }
        } catch (IOException e) {
            throw new NetBSException("Trouble with creating instance", e);
        }
    }

    void play(File mapFile);
}
