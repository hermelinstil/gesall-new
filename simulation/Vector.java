package simulation;

import java.io.Serializable;

/**
 * Created by molotov on 10/29/14.
 */
public class Vector implements Serializable {

    public float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void add(Vector other) {
        x += other.x;
        y += other.y;
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.x + v2.x, v1.y + v2.y);
    }

    public void sub(Vector other) {
        x -= other.x;
        y -= other.y;
    }

    public static Vector sub(Vector v1, Vector v2) {
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    public void mult(float value) {
        x *= value;
        y *= value;
    }

    public void div(float value) {
        x /= value;
        y /= value;
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        div(magnitude());
    }

    public void limit(float max) {
        if(magnitude() > max) {
            normalize();
            mult(max);
        }
    }

    public Vector copy() {
        return new Vector(x, y);
    }

    @Override
    public String toString() {
        return "[ X: " + x + " Y: " + y + " ]";
    }
}
