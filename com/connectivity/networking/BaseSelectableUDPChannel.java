package com.connectivity.networking;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * Abstract base UDP Channel - base class for UDP Multicast implementations
 * As the selector is responsible to provide read and write capabilities
 * This class only requires you to implement the defined naming reference
 * to the socket, which is transport, and save the multicastGroup.
 * 
 * @author douglasvinter
 * 
 */
public abstract class BaseSelectableUDPChannel {
	/**
	 * UDP Socket channel used to implement a Multicast based protocol
	 */
	protected DatagramChannel transport;
	/**
	 * Target Multicast group and port used to send or bind
	 */
	protected InetSocketAddress multicastGroup;
}
