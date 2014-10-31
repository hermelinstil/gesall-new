package simulation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by molotov on 10/29/14.
 */
public class Simulation {


    private ArrayList<Entity> entities;
    private ArrayList<Entity> temp;


    public Simulation() {
        entities = new ArrayList<Entity>();
        temp = new ArrayList<Entity>();

        entities.add(new Entity(10, 5));
        entities.add(new Entity(60, 400));
        entities.add(new Entity(800, 50));
    }

    public void step() {
        if(!temp.isEmpty()) {
            entities.addAll(temp);
            temp.clear();
        }

        for(Entity entity : entities) {
            entity.update();
        }
    }

    public byte[] getState() {
        /*ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bao);
            oos.writeObject(entities);
            oos.close();
            bao.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Size of byte array: " + bao.toByteArray().length);
        return bao.toByteArray();*/

        ByteBuffer buffer = ByteBuffer.allocate(entities.size() * 8);

        for(Entity e : entities) {
            buffer.put(e.toByteArray());
        }

        return buffer.array();
    }

    public void addNewEntity(float x, float y) {
        temp.add(new Entity(x, y));
    }
}
