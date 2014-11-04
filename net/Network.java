package net;

import system.Renderer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by molotov on 10/31/14.
 */
public abstract class Network implements Runnable {

    protected DatagramSocket socket;
    protected int localPort;

    protected Network(int _localPort) {
        localPort = _localPort;

        try {
            socket = new DatagramSocket(localPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static Client initClient(Renderer renderer, String[] args) {
        Client client = null;

        try {
            client = new Client(renderer,
                       args[0],                         //host IP
                       Integer.parseInt(args[1]),       //localport
                       Integer.parseInt(args[2]));      //remotePort
        } catch (ArrayIndexOutOfBoundsException a) {}

        return client;
    }

    public static void initServer() {
        new Server(2000);
    }

    @Override
    public void run() {
        byte[] byteArray = new byte[8192];
        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length);

        while(true) {
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            handleData(packet);
        }
    }

    public abstract void send(String data);
    public abstract void handleData(DatagramPacket data);

    public void close() {
        socket.close();
    }
}
