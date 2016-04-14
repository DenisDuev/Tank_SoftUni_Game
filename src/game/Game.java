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

import java.util.Random;

public class Game implements Runnable {

    private static final int MATRIX_ROWS = 20;
    private static final int MATRIX_COLS = 20;

    private Stage stage;
    private GraphicsContext gc;
    private static int[][] matrix = new int[MATRIX_ROWS][MATRIX_COLS];


    public Game(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Group root = new Group();

        final Canvas canvas = new Canvas(Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);


        Tank tank1 = new Tank("Denis", 100, 50, 50);
        Tank tank2 = new Tank("Pesho", 300, 100, 100);
        InitialiseMatrix();
        InputHandler inputHandler = new InputHandler(s, tank1, tank2);
        root.getChildren().add(canvas);
        stage.setScene(s);

        stage.show();

        new AnimationTimer() {
            Image background = new Image("resources/gameplay_background.png");
            Image wall = new Image("resources/wall_ordinary.png");

            @Override
            public void handle(long now) {
                gc.drawImage(background, 0, 0);
                gc.drawImage(tank1.getImage(), tank1.getX(), tank1.getY());
                gc.drawImage(tank2.getImage(), tank2.getX(), tank2.getY());
                DrawWalls(gc, wall);
                inputHandler.refresh();

            }

        }.start();

    }

    //TODO fix matrix generation
    private void InitialiseMatrix() {
        Random random = new Random();
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                int randomNum = random.nextInt(2);
                this.matrix[row][col] = randomNum;
            }
        }
    }

    private void DrawWalls(GraphicsContext gc, Image wall) {
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                if (matrix[row][col] != 0) {
                    gc.drawImage(wall, col * 30, row * 30);
                }
            }
        }
    }

    @Override
    public void run() {

    }

}
