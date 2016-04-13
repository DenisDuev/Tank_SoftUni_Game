package game;

import constants.Constants;
import game.input.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Tanks.Tank;

public class Game implements Runnable {

    Stage stage;
    GraphicsContext gc;

    public Game(Stage stage) {
        this.stage = stage;
    }

    public void start(){
        Group root = new Group();

        final Canvas canvas = new Canvas(Constants.WINDOWS_WIDTH,Constants.WINDOWS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);


        Tank tank1 = new Tank("Denis",100,50,50);
        Tank tank2 = new Tank("Pesho",300,100,100);
        InputHandler inputHandler = new InputHandler(s,tank1, tank2);
        root.getChildren().add(canvas);
        stage.setScene(s);

        stage.show();
        //Tank tank = new Tank("Denis",100,50,50);

        new AnimationTimer(){
            int x;
            int y;


            Image background = new Image("resources/gameplay_background.png");

            @Override
            public void handle(long now) {
                gc.drawImage(background,0,0);
                gc.drawImage(tank1.getUpImage(), tank1.getX(), tank1.getY());
                gc.drawImage(tank2.getUpImage(), tank2.getX(), tank2.getY());
                inputHandler.refresh();

            }

        }.start();

    }

    @Override
    public void run() {

    }

}
