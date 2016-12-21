package com.connectivity.playground;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.connectivity.loop.IOLoop;
import com.connectivity.networking.Protocols;
import com.connectivity.networking.SocketFactory;
import com.connectivity.networking.UDPMulticastServer;

public class Run {

	public static void main(String[] args) {
		// Sample to run the project
		try {
			IOLoop io = new IOLoop();
			UDPMulticastServer upnp = (UDPMulticastServer) SocketFactory.getUDPChannel(Protocols.MULTICAST_SERVER,
					new InetSocketAddress("239.255.255.250", 1900), "UPnP");
			UDPMulticastServer mDNS = (UDPMulticastServer) SocketFactory.getUDPChannel(Protocols.MULTICAST_SERVER,
					new InetSocketAddress("224.0.0.251", 5353), "mDNS");
			UDPMulticastServer slpAdmin = (UDPMulticastServer) SocketFactory.getUDPChannel(Protocols.MULTICAST_SERVER,
					new InetSocketAddress("239.255.255.253", 427), "SLP Admin");
			UDPMulticastServer slpGeneral = (UDPMulticastServer) SocketFactory.getUDPChannel(Protocols.MULTICAST_SERVER,
					new InetSocketAddress("224.0.1.22", 427), "SLP General");
			UDPMulticastServer slpDiscovery = (UDPMulticastServer) SocketFactory.getUDPChannel(
					Protocols.MULTICAST_SERVER, new InetSocketAddress("224.0.1.35", 427),
					"SLP Directory Agent Discovery");
			upnp.register(io.selector);
			mDNS.register(io.selector);
			slpAdmin.register(io.selector);
			slpGeneral.register(io.selector);
			slpDiscovery.register(io.selector);
			io.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
