package net;

import simulation.Entity;
import system.Input;
import system.Renderer;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by molotov on 10/29/14.
 */
public class Client extends Network implements Runnable {

    private Renderer renderer;
    private InetAddress host;
    private int remotePort;

    protected Client(Renderer renderer, String host, int localPort, int remotePort) {
        super(localPort);

        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.remotePort = remotePort;
        this.renderer = renderer;

        new Thread(this).start();
        send("N");
    }

    @Override
    public void run() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(Input.isLeftMouseIsDown()) {
                    String input = "I" + Input.getMousePosition().getX() + " " + Input.getMousePosition().getY();
                    send(input);
                }
            }
        }, 0, 1000);

        super.run();
    }

    @Override
    public void handleData(DatagramPacket data) {
        /*ByteArrayInputStream bis = new ByteArrayInputStream(data.getData());
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            ArrayList<Entity> list = (ArrayList<Entity>) ois.readObject ();
            renderer.render(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        byte[] byteArray = data.getData();

        ArrayList<Entity> list = new ArrayList<Entity>(byteArray.length / 8);

        ByteBuffer buffer = ByteBuffer.wrap(byteArray);

        while(buffer.hasRemaining()) {
            float x = buffer.getFloat(); float y = buffer.getFloat();
            if(x != 0 && y != 0) // kan inte rendera ut på plats 0, 0 (fullösning) eftersom paketet alltid är fullt... hur trimmar man den?
                list.add(new Entity(x, y));
        }

        renderer.render(list);
    }

    @Override
    public void send(String dataString) {
        byte[] byteArray = dataString.getBytes();

        DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, host, remotePort);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
