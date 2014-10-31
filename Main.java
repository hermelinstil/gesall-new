import net.Client;
import net.Network;
import system.Renderer;

import javax.swing.*;


/**
 * Created by molotov on 10/31/14.
 */
public class Main extends JFrame {

    private Client client;
    private Renderer renderer;

    void init() {
        renderer = new Renderer(this);

        if(renderer.query("Do you want to run the server?")) {
            Network.initServer();
        }

        while(client == null) {
            client = Network.initClient(renderer, renderer.queryText("Network arguments").split(" "));
        }
    }

    public static void main(String[] args) {
        new Main().init();
    }
}
