package game.input;

import javafx.scene.Scene;
import objects.Tanks.Tank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Denis on 13.4.2016 ã..
 */
public class InputHandler {
    private Scene scene;
    private Tank tank1;
    private Tank tank2;
    private HashSet<String> input;

    public InputHandler(Scene scene, Tank player1, Tank player2) {
        this.scene = scene;
        this.tank1 = player1;
        this.tank2 = player2;
        this.input = new HashSet<String>();
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
        if (this.input.contains("LEFT")) {
            this.tank1.move(-1, 0);
        } else
        if (this.input.contains("RIGHT")) {
            this.tank1.move(1, 0);
        } else
        if (this.input.contains("UP")) {
            this.tank1.move(0, -1);
        }else
        if (this.input.contains("DOWN")) {
            this.tank1.move(0, 1);
        }

        if (this.input.contains("SPACE")) {
            //TODO fire
            System.out.println(this.tank1.getName() + " fire");
        }

        if (this.input.contains("A")) {
            this.tank2.move(-1, 0);
        } else if (this.input.contains("D")) {
            this.tank2.move(1, 0);
        } else if (this.input.contains("W")) {
            this.tank2.move(0, -1);
        } else if (this.input.contains("S")) {
            this.tank2.move(0, 1);
        }
        if (this.input.contains("ENTER")){
            //TODO fire
            System.out.println(this.tank2.getName() + " fire");
        }
    }
}

