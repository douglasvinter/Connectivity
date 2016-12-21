package com.connectivity.loop;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.logging.Logger;

import com.connectivity.networking.Protocols;
import com.connectivity.networking.SocketIdentifier;
import com.connectivity.networking.UDPMulticastServer;

public class IOLoop implements Runnable {

	public Integer MAX_RECV = 1024;
	public Selector selector = null;
	public static final Logger log = Logger.getLogger(UDPMulticastServer.class.getName());

	public IOLoop() throws IOException {
		selector = Selector.open();
	}

	@Override
	public void run() {
		while (selector.isOpen()) {
			// Waiting for I/O any completion
			try {
				selector.select();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				cleanUp();
				e.printStackTrace();
			}
			Set<SelectionKey> ioReady = selector.selectedKeys();
			ioReady.parallelStream().forEach((channel) -> this.handleIO(channel));
		}

	}

	private void handleIO(SelectionKey channel) {
		if (channel.isAcceptable()) {
			// TO - DO
		} else if (channel.isConnectable()) {
			// TO - DO
		} else if (channel.isReadable()) {
			SocketIdentifier identity = (SocketIdentifier) channel.attachment();
			if (identity.getType().equals(Protocols.UDP)) {
				handleDgramRecv((DatagramChannel) channel.channel(), identity);
			} else if (identity.getType().equals(Protocols.TCP)) {
				handleStreamRecv((SocketChannel) channel.channel(), identity);
			} else {
				log.severe("Unsupported socket was registered, ignoring.");
			}
		} else if (channel.isWritable()) {
			// TO - DO
		}
	}

	private void handleStreamRecv(SocketChannel transport, SocketIdentifier identity) {
		// TO-DO
	}

	private void handleDgramRecv(DatagramChannel transport, SocketIdentifier identity) {
		ByteBuffer recv = ByteBuffer.allocate(MAX_RECV);
		try {
			transport.receive(recv);
			recv.flip();
			byte[] data = new byte[recv.limit()];
			recv.get(data);
			log.info("Received: " + new String(data, "utf-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String data = new String(recv.array()).trim();
		log.info("Received data for Transport: " + identity.getImplementedProtocol() + "\n" + data);
	}

	private void cleanUp() {
		try {
			selector.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
