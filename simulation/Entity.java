package simulation;

import system.Renderer;

import java.awt.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by molotov on 10/31/14.
 */
public class Entity implements Serializable {

    private Vector position,
                   velocity,
                   acceleration;


    private float mass;
    private int width, height;

    public Entity(float x, float y) {
        position = new Vector(x, y);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0.01f, 0.01f);

        //stämmer för tillfället överener med RENDERER
        width = 50;
        height = 50;
    }

    public void update() {
        velocity.add(acceleration);
        position.add(velocity);

        checkBorders();
    }

//    WRAP
//    private void checkBorders() {
//        if(position.x > Renderer.WIDTH) {
//            position.x = 0;
//        } else if(position.x < 0) {
//            position.x = Renderer.WIDTH;
//        }
//
//        if(position.y > Renderer.HEIGHT) {
//            position.y = 0;
//        } else if(position.y < 0) {
//            position.y = Renderer.HEIGHT;
//        }
//    }

    //BOUNCE
    private void checkBorders() {
        if(position.x > Renderer.WIDTH - width || position.x < 0) {
            velocity.x *= -1;
        }

        if(position.y > Renderer.HEIGHT - height || position.y < 0) {
            velocity.y *= -1;
        }
    }

    public Vector getPosition() {
        return position;
    }

    public byte[] toByteArray() {
        return ByteBuffer.allocate(8)
                    .putFloat(position.x)
                    .putFloat(position.y)
                    .array();
    }
}
