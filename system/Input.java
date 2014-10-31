package system;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

/**
 * Created by Stavling on 2014-10-24.
 */
public abstract class Input {

    private static MouseInput mi = new MouseInput();
    private static KeyBoardInput ki = new KeyBoardInput();

    private static HashSet<Integer> keyState = new HashSet<Integer>();
    private static Point mousePosition = new Point();
    private static boolean mouseIsDown = false;

    private static boolean registred = false;


    public static void registerInputListeners(Canvas canvas) {
        canvas.addMouseListener(mi);
        canvas.addMouseMotionListener(mi);
        canvas.addKeyListener(ki);
        registred = true;
    }

    public static HashSet<Integer> getKeyState() {
        if(registred) {
            return keyState;
        }
        throw new IllegalStateException("systems.system.Input has not been registred to any canvas");
    }

    public static Point getMousePosition() {
        return mousePosition;
    }

    public static boolean isMouseDown() {
        return mouseIsDown;
    }

    private static class MouseInput extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            mousePosition.setLocation(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mouseIsDown = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseIsDown = false;
        }
    }

    private static class KeyBoardInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            keyState.add(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyState.remove(e.getKeyCode());
        }
    }

}
