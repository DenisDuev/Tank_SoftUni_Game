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
import objects.Bullet;
import objects.Tanks.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Runnable {

    private static final int MATRIX_ROWS = 20;
    private static final int MATRIX_COLS = 20;

    private Stage stage;
    private GraphicsContext gc;
    private static int[][] matrix = new int[MATRIX_ROWS][MATRIX_COLS];
    private boolean hasTwoPlayers;
    private CollisionDetector collisionDetector;
    private BulletHandler bulletHandler;


    public Game(Stage stage, boolean hasTwoPlayers) {
        this.stage = stage;
        this.hasTwoPlayers = hasTwoPlayers;
    }

    public void start() {
        Group root = new Group();

        final Canvas canvas = new Canvas(Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);


        Tank tank1 = new Tank("Denis", 100, 50, 50);
        Tank tank2 = new Tank("Pesho", 300, 100, 100);
        List<Tank> tanks = new ArrayList<>();
        tanks.add(tank1);
        tanks.add(tank2);
        collisionDetector = new CollisionDetector(matrix,tanks);
        tank1.setCollisionDetector(collisionDetector);
        tank2.setCollisionDetector(collisionDetector);

        InitialiseMatrix();
        bulletHandler = new BulletHandler(collisionDetector);
        InputHandler inputHandler = new InputHandler(s, tank1, tank2, hasTwoPlayers, bulletHandler);

        root.getChildren().add(canvas);
        stage.setScene(s);

        stage.show();

        new AnimationTimer() {
            Image background = new Image("resources/gameplay_background.png");
            Image wall = new Image("resources/wall_ordinary.png");
            Image bird = new Image("resources/bird.png");

            @Override
            public void handle(long now) {
                gc.drawImage(background, 0, 0);
                DrawWalls(gc, wall);
                gc.drawImage(bird,280,570);
                DrawBullets(gc);
                gc.drawImage(tank1.getImage(), tank1.getX(), tank1.getY());
                if (hasTwoPlayers) {
                    gc.drawImage(tank2.getImage(), tank2.getX(), tank2.getY());
                }
                inputHandler.refresh();

            }

        }.start();

    }

    private void DrawBullets(GraphicsContext gc) {
        List<Bullet> bullets = bulletHandler.GetBulletsToDraw();
        for (Bullet bullet : bullets) {
            gc.drawImage(bullet.getImage(), bullet.getX(), bullet.getY());
        }
    }

    //TODO fix matrix generation
    private void InitialiseMatrix() {
        Random random = new Random();
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                int randomNum = random.nextInt(15);
                this.matrix[row][col] = randomNum == 0 ? 1 : 0;
            }
        }
    }

    private void DrawWalls(GraphicsContext gc, Image wall) {
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                if (matrix[row][col] != 0) {
                    gc.drawImage(wall, col * Constants.MATRIX_CELL_SIZE, row * Constants.MATRIX_CELL_SIZE);
                }
            }
        }
    }

    @Override
    public void run() {

    }

}
