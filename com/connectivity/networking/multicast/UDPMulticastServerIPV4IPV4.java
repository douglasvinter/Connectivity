package com.connectivity.networking.multicast;

import com.connectivity.loop.IOLoop;
import com.connectivity.networking.domain.Protocols;
import com.connectivity.networking.domain.SocketType;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

/**
 * @author douglasvinter
 */
public class UDPMulticastServerIPV4IPV4 extends BaseSelectableUDPChannelIPV4 {

    private static final Logger log = Logger.getLogger(UDPMulticastServerIPV4IPV4.class.getName());
    private String protocolName;

    public UDPMulticastServerIPV4IPV4(Protocols protocol, IOLoop ioLoop) {
        this.multicastGroup = protocol.getInetSocketAddress();
        this.protocolName = protocol.getProtocolName();
        this.ioLoop = ioLoop;
    }

    public void startListening() throws IOException {
        try {
            transport = DatagramChannel.open(StandardProtocolFamily.INET)
                    .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                    .bind(new InetSocketAddress("0.0.0.0", multicastGroup.getPort()));
            transport.configureBlocking(false);
            joinGroups(multicastGroup.getAddress());
        } catch (SocketException e) {
            log.severe("Could create protocol server for: " + protocolName + ", please try with root user: "
                    + e.getMessage());
        } catch (IOException e) {
            log.severe("Error creating transport channel: " + e.getMessage());
        }
        identity.setSocketType(SocketType.IPV4_UDP);
        identity.setImplementedProtocol(protocolName);

        register();
    }
}
