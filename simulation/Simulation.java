package simulation;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by molotov on 10/29/14.
 */
public class Simulation {


    private ArrayList<Entity> entities;
    private ArrayList<Entity> temp;

    public static Random random = new Random();

    public Simulation() {
        entities = new ArrayList<Entity>();
        temp = new ArrayList<Entity>();

        for(int i = 0; i < 200; ++i) {
            entities.add(new Entity(random.nextInt(1230), random.nextInt(670)));
        }

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
