package com.connectivity.networking.factories;

import com.connectivity.loop.IOLoop;
import com.connectivity.networking.domain.Multicast;
import com.connectivity.networking.domain.Protocols;
import com.connectivity.networking.multicast.UDPMulticastServerIPV4IPV4;

import static com.connectivity.networking.domain.Protocols.*;

public class ProtocolFactory {

    private IOLoop ioLoop;

    public ProtocolFactory(IOLoop ioLoop) {
        this.ioLoop = ioLoop;
    }

    public UDPMulticastServerIPV4IPV4 getServerFor(Protocols protocol) {
        UDPMulticastServerIPV4IPV4 server = null;

        if (UPNP.equals(protocol)) {
            server = (UDPMulticastServerIPV4IPV4) SocketFactory.getUDPChannel(Multicast.SERVER, UPNP, this.ioLoop);
        } else if (MDNS.equals(protocol)) {
            server = (UDPMulticastServerIPV4IPV4) SocketFactory.getUDPChannel(Multicast.SERVER, MDNS, this.ioLoop);
        } else if (SLP_ADMIN.equals(protocol)) {
            server = (UDPMulticastServerIPV4IPV4) SocketFactory.getUDPChannel(Multicast.SERVER, SLP_ADMIN, this.ioLoop);
        } else if (SLP_GENERAL.equals(protocol)) {
            server = (UDPMulticastServerIPV4IPV4) SocketFactory.getUDPChannel(Multicast.SERVER, SLP_GENERAL, this.ioLoop);
        } else if (SLP_DIRECTORY.equals(protocol)) {
            server = (UDPMulticastServerIPV4IPV4) SocketFactory.getUDPChannel(Multicast.SERVER, SLP_DIRECTORY, this.ioLoop);
        }

        return server;
    }
}
