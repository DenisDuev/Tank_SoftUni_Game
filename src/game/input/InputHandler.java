package game.input;

import javafx.scene.Scene;
import objects.Tanks.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 13.4.2016 ã..
 */
public class InputHandler {
    private Scene scene;
    private Tank tank;
    private List<String> input;
    private long lastTime = System.nanoTime();

    public InputHandler(Scene scene, Tank player) {
        this.scene = scene;
        this.tank = player;
        this.input = new ArrayList<>();
    }

    public void refresh() {
        this.scene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if (!input.contains(code))
                        this.input.add(code);
                });

        this.scene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    this.input.remove(code);
                });
        handleKeys();
    }

    private void handleKeys() {
        //TODO two players
        if (this.input.contains("LEFT") || this.input.contains("A"))
            this.tank.move(-1, 0);
        if (this.input.contains("RIGHT") || this.input.contains("D"))
            this.tank.move(1, 0);
        if (this.input.contains("UP") || this.input.contains("W"))
            this.tank.move(0, -1);

        if (this.input.contains("DOWN") || this.input.contains("S"))
            this.tank.move(0, 1);

        if (this.input.contains("SPACE")) {
            //TODO fire
        }
    }
}

