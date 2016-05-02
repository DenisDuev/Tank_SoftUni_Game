package input;

import game.ObjectHandler;
import javafx.scene.Scene;
import objects.game_objects.tanks.Tank;

import java.util.HashSet;

public class InputHandler {
    private Scene scene;
    private Tank tank1;
    private Tank tank2;
    private HashSet<String> input;
    private boolean hasTwoPlayers;
    private ObjectHandler bulletHandler;
    private long lastSystemTime = System.nanoTime();

    public InputHandler(Scene scene, Tank player1, Tank player2, boolean hasTwoPlayers, ObjectHandler bulletHandler) {
        this.scene = scene;
        this.tank1 = player1;
        this.tank2 = player2;
        this.input = new HashSet<>();
        this.hasTwoPlayers = hasTwoPlayers;
        this.bulletHandler = bulletHandler;
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

        long currentSystemTime = System.nanoTime();

        double elapsedSystemTime = (currentSystemTime - lastSystemTime) / 1_000_000_00.0;

        if (this.tank1.isAlive()){
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

            if (this.input.contains("ENTER") && elapsedSystemTime > 6) {
                bulletHandler.AddBullet(tank1.spawnBullet());
                this.lastSystemTime = currentSystemTime;

            }
        }

        if (hasTwoPlayers && this.tank2.isAlive()){
            if (this.input.contains("A")) {

                this.tank2.move(-1, 0);
            } else if (this.input.contains("D")) {
                this.tank2.move(1, 0);
            } else if (this.input.contains("W")) {
                this.tank2.move(0, -1);
            } else if (this.input.contains("S")) {
                this.tank2.move(0, 1);
            }
            if (this.input.contains("Q") && elapsedSystemTime > 6){
                this.bulletHandler.AddBullet(tank2.spawnBullet());
                this.lastSystemTime = currentSystemTime;
            }
        }

    }
}

