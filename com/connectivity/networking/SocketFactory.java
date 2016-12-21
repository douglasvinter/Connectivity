package com.connectivity.networking;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 
 * @author douglasvinter
 *
 */
public class SocketFactory {
	
	/**
	 * 
	 * @param endpointType
	 * @param multicastGroup
	 * @param protocolName
	 * @return
	 * @throws IOException
	 */
	public static BaseSelectableUDPChannel getUDPChannel(Protocols endpointType, InetSocketAddress multicastGroup, String protocolName) throws IOException {
		if (endpointType.equals(Protocols.MULTICAST_SERVER)) {
			return new UDPMulticastServer(multicastGroup, protocolName);
		} else if (endpointType.equals(Protocols.MULTICAST_CLIENT)) {
			return new UDPMulticastClient(multicastGroup, protocolName);
		}

		return null;
	}
	
}
