package com.connectivity.loop;

import com.connectivity.networking.domain.ProtocolIdentifier;
import com.connectivity.networking.domain.SocketType;
import com.connectivity.networking.multicast.UDPMulticastServerIPV4IPV4;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class IOLoop implements Runnable {

    public static final Logger log = Logger.getLogger(UDPMulticastServerIPV4IPV4.class.getName());
    private static final long SELECTOR_TIMEOUT = 10000L;
    private final Selector selector;
    public Integer MAX_RECV = 1024;

    public IOLoop() throws IOException {
        selector = Selector.open();
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() {
        while (selector.isOpen()) {
            try {
                if (selector.select(SELECTOR_TIMEOUT) > 0) {
                    selector.selectedKeys()
                            .forEach(this::handleIO);
                }
            } catch (IOException e) {
                log.severe("Error on IOSelector: " + e.getMessage());
                cleanUp();
            }
        }
    }

    private void handleIO(SelectionKey channel) {
        if (channel.isReadable()) {
            ProtocolIdentifier identity = (ProtocolIdentifier) channel.attachment();
            if (identity.getSocketType().equals(SocketType.IPV4_UDP)) {
                handleDgramRecv((DatagramChannel) channel.channel(), identity);
            } else if (identity.getSocketType().equals(SocketType.IPV4_TCP)) {
                handleStreamRecv((SocketChannel) channel.channel(), identity);
            } else {
                log.severe("Unsupported socket was registered, ignoring.");
            }
        }
    }

    private void handleStreamRecv(SocketChannel transport, ProtocolIdentifier identity) {
        log.info("TCP not implemented yet");
    }

    private void handleDgramRecv(DatagramChannel transport, ProtocolIdentifier identity) {
        ByteBuffer buffer = ByteBuffer.allocate(MAX_RECV);

        try {
            transport.receive(buffer);
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);

            log.info("Received data for " + identity.getImplementedProtocol() + ":\n" + new String(data, StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.severe("Could not read socket for " + identity.getImplementedProtocol() + ", cause: " + e.getMessage());
        }
    }

    private void cleanUp() {
        try {
            selector.close();
        } catch (IOException e) {
            log.severe("Error closing selector: " + e.getMessage());
        }
    }
}
