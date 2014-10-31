package system;

import simulation.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by Stavling on 2014-10-22.
 */
public class Renderer {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private static JFrame frame;

    private BufferStrategy bufferStrategy;

    public Renderer(JFrame frame) {
        this.frame = frame;
        bufferStrategy = initGUI();
    }

    public void render(ArrayList<Entity> renderList) {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);

        for(Entity entity : renderList) {
            g.fillOval((int)entity.getPosition().x,
                       (int)entity.getPosition().y,
                        50,
                        50);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        bufferStrategy.show();
    }

    private BufferStrategy initGUI() {
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        Input.registerInputListeners(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);

        canvas.requestFocus();

        return canvas.getBufferStrategy();
    }

    public boolean query(String string) {
        return JOptionPane.showConfirmDialog(frame, string) == JOptionPane.OK_OPTION;
    }

    public String queryText(String string) {
        return JOptionPane.showInputDialog(frame, string).trim();
    }
}
