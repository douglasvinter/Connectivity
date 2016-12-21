package com.connectivity.networking;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collections;
import java.util.logging.Logger;

/**
 * 
 * @author douglasvinter
 *
 */
public class UDPMulticastServer extends BaseSelectableUDPChannel {

	private boolean isRegistered = false;
	private SocketIdentifier identity = new SocketIdentifier();
	private static final Logger log = Logger.getLogger(UDPMulticastServer.class.getName());

	public UDPMulticastServer(InetSocketAddress multicastGroup, String protocolName) {
		// Force IPV4 usage
		// Enables re-use address
		// Bind to ANY_ADDR + Protocol port
		// Enforcing all network interfaces (valid ones) register
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
		identity.setType(Protocols.UDP);
		identity.setImplementedProtocol(protocolName);
		this.multicastGroup = multicastGroup;
	}

	private void joinGroups(InetAddress group) throws IOException {
		for (NetworkInterface netIf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
			if (Collections.list(netIf.getInetAddresses()).size() > 0 && !netIf.isLoopback()
					&& netIf.supportsMulticast() && !netIf.isVirtual() && !netIf.isPointToPoint()) {
				for (InetAddress ifAddr : Collections.list(netIf.getInetAddresses())) {
					if (ifAddr instanceof Inet4Address) {
						try {
							this.transport.join(group, netIf);
						} catch (SocketException e) {
							log.severe("Cannot join group " + group.toString() + " reason: " + e.getMessage());
						}
						log.info("Joining group: " + group.toString() + " on interface: " + netIf.getDisplayName());
					}
				}
			}
		}
	}

	public void register(Selector selector) throws IOException {
		if (!isRegistered && this.transport != null) {
			this.isRegistered = true;
			try {
				this.transport.register(selector, SelectionKey.OP_READ, identity);
			} catch (ClosedChannelException e) {
				log.severe("Error while registering selector: " + e.getMessage());
				throw new IOException("Error registering socket " + e.getMessage());
			}
		} else {
			log.severe("Could not register channel");
		}
	}
}
