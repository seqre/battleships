package seqre.battleships.network;

import java.net.*;


public class NetUtils {
    public static InetAddress findAddress() throws NetBSException {
        try {
            //TODO: Dynamic choice of interface / option added to parser
            NetworkInterface en0 = NetworkInterface.getByName("en0");
            return en0.inetAddresses()
                    .filter(a -> a instanceof Inet4Address)
                    .findFirst()
                    .orElse(InetAddress.getLocalHost());
        } catch (SocketException | UnknownHostException e) {
            throw new NetBSException("Error during obtaining address", e);
        }
    }
}
