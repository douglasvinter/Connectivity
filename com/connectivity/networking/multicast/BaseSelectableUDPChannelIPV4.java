package com.connectivity.networking.multicast;

import com.connectivity.loop.IOLoop;
import com.connectivity.networking.domain.ProtocolIdentifier;

import java.io.IOException;
import java.net.*;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.logging.Logger;

/**
 * Abstract base UDP Channel - base class for UDP Multicast implementations
 * As the selector is responsible to provide read and write capabilities
 * This class only requires you to implement the defined naming reference
 * to the socket, which is transport, and save the multicastGroup.
 *
 * @author douglasvinter
 */
public abstract class BaseSelectableUDPChannelIPV4 {

    private static final Logger log = Logger.getLogger(BaseSelectableUDPChannelIPV4.class.getName());

    /**
     * UDP Socket channel used to implement a Multicast based protocol
     */
    protected DatagramChannel transport;
    /**
     * Target Multicast group and port used to send or bind
     */
    protected InetSocketAddress multicastGroup;
    /**
     * The protocol identification for client or server
     */
    protected ProtocolIdentifier identity = new ProtocolIdentifier();
    /**
     * The Runnable selectable Loop
     */
    protected IOLoop ioLoop;

    void joinGroups(InetAddress group) throws SocketException {
        NetworkInterface.getNetworkInterfaces().asIterator().forEachRemaining(netIf -> {
            try {
                if (!netIf.isLoopback() && netIf.supportsMulticast() && !netIf.isVirtual() && !netIf.isPointToPoint()) {
                    netIf.getInetAddresses().asIterator().forEachRemaining(inetAddress -> {
                        if (inetAddress instanceof Inet4Address) {
                            try {
                                this.transport.join(group, netIf);
                                log.info("Joined multicast group: " + group.toString() + " on interface: " + netIf.getDisplayName());
                            } catch (IOException e) {
                                log.severe("Cannot join group " + group.toString() + " reason: " + e.getMessage());
                            }
                        }
                    });
                }
            } catch (SocketException e) {
                log.severe("Error parsing interfaces: " + e.getMessage());
            }
        });
    }

    public void register() throws IOException {
        if (this.transport != null) {
            try {
                this.transport.register(this.ioLoop.getSelector(), SelectionKey.OP_READ, identity);
            } catch (ClosedChannelException e) {
                log.severe("Error while registering selector: " + e.getMessage());
                throw new IOException("Error registering socket " + e.getMessage());
            }
        }
    }
}
