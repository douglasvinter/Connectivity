package com.connectivity.networking.domain;

import java.net.InetSocketAddress;

/**
 * Base supported Protocols
 *
 * @author dbranco
 */
public enum Protocols {
    UPNP(new InetSocketAddress("239.255.255.250", 1900), "UPnP"),
    MDNS(new InetSocketAddress("224.0.0.251", 5353), "mDNS"),
    SLP_ADMIN(new InetSocketAddress("239.255.255.253", 427), "SLP Admin"),
    SLP_GENERAL(new InetSocketAddress("224.0.1.22", 427), "SLP General"),
    SLP_DIRECTORY(new InetSocketAddress("224.0.1.35", 427), "SLP Directory Agent Discovery");

    private final InetSocketAddress inetSocketAddress;
    private final String protocolName;

    Protocols(InetSocketAddress inetSocketAddress, String protocolName) {
        this.inetSocketAddress = inetSocketAddress;
        this.protocolName = protocolName;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public String getProtocolName() {
        return protocolName;
    }
}
