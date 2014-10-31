package net;

import simulation.Simulation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by molotov on 10/31/14.
 */
public class Server extends Network {
                  //ID                 Address      port
    private HashMap<Integer, ConnectionEntry> connections;
    private int connectionCount = 0;

    private Simulation simulation;

    public Server(int _localPort) {
        super(_localPort);
        connections = new HashMap<Integer, ConnectionEntry>();
        simulation = new Simulation();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Server is running");

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //broadcast("ECHO");
                simulation.step();
                broadcast(simulation.getState());
            }
        }, 0, 20);

        super.run();

    }

    //for testing
    public void broadcast(String dataString) {
        for(Map.Entry<InetAddress, Integer> connection : connections.values()) {
            send(dataString, connection.getKey(), connection.getValue());
        }
    }

    public void broadcast(byte[] dataString) {
        for(Map.Entry<InetAddress, Integer> connection : connections.values()) {
            send(dataString, connection.getKey(), connection.getValue());
        }
    }

    public void send(byte[] data, InetAddress address, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String dataString, InetAddress address, int port) {
        send(dataString.getBytes(), address, port);
    }

    @Override
    public void handleData(DatagramPacket data) {
        String dataString = new String(data.getData());
        char code = dataString.charAt(0);

        switch(code) {
            case 'N':   newConnection(data);
                break;
            case 'I':   newInput(dataString.substring(1, dataString.length() - 1));
                break;
            default:
                System.out.println("SERVER DEFAULT: > " + dataString);
        }
    }

    private void newConnection(DatagramPacket data) {
        connections.put(++connectionCount, new ConnectionEntry(data.getAddress(), data.getPort()));
    }

    private void newInput(String input) {
        String[] coords = input.split(" ");
        simulation.addNewEntity(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]));
    }

    @Override
    public void send(String dataString) {

    }

    final class ConnectionEntry<InetAddress, Integer> implements Map.Entry<InetAddress, Integer> {
        private  InetAddress key;
        private Integer value;

        public ConnectionEntry(InetAddress key, Integer port) {
            this.key = key;
            this.value = port;
        }

        @Override
        public InetAddress getKey() {
            return key;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public Integer setValue(Integer value) {
            this.value = value;
            return null;
        }
    }
}
