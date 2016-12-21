package com.connectivity.networking;

/**
 * 
 * @author dbranco
 *
 */
public class SocketIdentifier {
	
	/**
	 * 
	 */
	private Protocols type;
	/**
	 * String representation of the target protocol i.e SLP, SSDP, UPnP, mDNS
	 */
	private String ImplementedProtocol;
	
	public Protocols getType() {
		return type;
	}
	public void setType(Protocols type) {
		this.type = type;
	}
	public String getImplementedProtocol() {
		return ImplementedProtocol;
	}
	public void setImplementedProtocol(String ImplementedProtocol) {
		this.ImplementedProtocol = ImplementedProtocol;
	}
	

}
