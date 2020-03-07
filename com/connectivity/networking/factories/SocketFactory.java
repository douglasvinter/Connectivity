package com.connectivity.networking.factories;

import com.connectivity.loop.IOLoop;
import com.connectivity.networking.domain.Multicast;
import com.connectivity.networking.domain.Protocols;
import com.connectivity.networking.multicast.BaseSelectableUDPChannelIPV4;
import com.connectivity.networking.multicast.UDPMulticastClientIPV4IPV4;
import com.connectivity.networking.multicast.UDPMulticastServerIPV4IPV4;

import java.net.InetSocketAddress;

/**
 * @author douglasvinter
 */
public class SocketFactory {

    public static BaseSelectableUDPChannelIPV4 getUDPChannel(Multicast endpointType, Protocols protocol, IOLoop ioLoop) {
        if (Multicast.SERVER.equals(endpointType)) {
            return new UDPMulticastServerIPV4IPV4(protocol, ioLoop);
        } else if (Multicast.CLIENT.equals(endpointType)) {
            return new UDPMulticastClientIPV4IPV4(protocol, ioLoop);
        }

        return null;
    }
}
