package com.connectivity.networking.domain;

/**
 * @author dbranco
 */
public class ProtocolIdentifier {

    /**
     * Transport Type
     */
    private SocketType type;
    /**
     * String representation of the target protocol i.e SLP, SSDP, UPnP, mDNS
     */
    private String ImplementedProtocol;

    public SocketType getSocketType() {
        return type;
    }

    public void setSocketType(SocketType type) {
        this.type = type;
    }

    public String getImplementedProtocol() {
        return ImplementedProtocol;
    }

    public void setImplementedProtocol(String ImplementedProtocol) {
        this.ImplementedProtocol = ImplementedProtocol;
    }


}
