package simulation;

import system.Renderer;

import java.io.Serializable;

/**
 * Created by molotov on 10/31/14.
 */
public class Entity implements Serializable {

    private Vector position,
                   velocity,
                   acceleration;

    public Entity(float x, float y) {
        position = new Vector(x, y);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0.01f, 0.01f);
    }

    public void update() {
        velocity.add(acceleration);
        position.add(velocity);

        checkBorders();
    }

    private void checkBorders() {
        if(position.x > Renderer.WIDTH) {
            position.x = 0;
        } else if(position.x < 0) {
            position.x = Renderer.WIDTH;
        }

        if(position.y > Renderer.HEIGHT) {
            position.y = 0;
        } else if(position.y < 0) {
            position.y = Renderer.HEIGHT;
        }
    }

    public Vector getPosition() {
        return position;
    }
}
