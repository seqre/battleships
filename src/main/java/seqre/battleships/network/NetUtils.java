package seqre.battleships.network;

import java.net.*;


public class NetUtils {
    public static InetAddress findAddress() throws NetException {
        try {
//            NetworkInterface.getNetworkInterfaces()
//                    .asIterator()
//                    .forEachRemaining(
//                            networkInterface -> System.out.println(networkInterface.getInterfaceAddresses())
//                    );

            NetworkInterface en0 = NetworkInterface.getByName("en0");
            return en0.inetAddresses()
                    .filter(a -> a instanceof Inet4Address)
                    .findFirst()
                    .orElse(InetAddress.getLocalHost());
        } catch (SocketException | UnknownHostException e) {
            throw new NetException("Error during obtaining address", e);
        }
    }
}
