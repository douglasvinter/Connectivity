package com.connectivity.playground;

import com.connectivity.loop.IOLoop;
import com.connectivity.networking.domain.Protocols;
import com.connectivity.networking.factories.ProtocolFactory;

import java.io.IOException;

public class Run {

    // Sample to run the project
    public static void main(String[] args) {
        try {
            IOLoop io = new IOLoop();
            ProtocolFactory factory = new ProtocolFactory(io);

            factory.getServerFor(Protocols.UPNP).startListening();
            factory.getServerFor(Protocols.MDNS).startListening();
            factory.getServerFor(Protocols.SLP_ADMIN).startListening();
            factory.getServerFor(Protocols.SLP_GENERAL).startListening();
            factory.getServerFor(Protocols.SLP_DIRECTORY).startListening();

            io.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
