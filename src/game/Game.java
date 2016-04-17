package game;

import constants.Constants;
import game.input.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import objects.Explosion;
import objects.Bullet;
import objects.Tanks.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game{

    private Stage stage;
    private GraphicsContext gc;
    private static int[][] matrix = new int[Constants.MATRIX_ROWS][Constants.MATRIX_COLS];
    private boolean hasTwoPlayers;
    private CollisionDetector collisionDetector;
    private ObjectHandler bulletHandler;
    List<Explosion> booms = new ArrayList<>();


    public Game(Stage stage, boolean hasTwoPlayers) {
        this.stage = stage;
        this.hasTwoPlayers = hasTwoPlayers;
    }

    public void start() {
        BorderPane root = new BorderPane();

        final Canvas canvas = new Canvas(Constants.WINDOWS_HEIGHT, Constants.WINDOWS_HEIGHT);
        root.setLeft(canvas);
        gc = canvas.getGraphicsContext2D();
        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);

        VBox leftDisplay = new VBox(Constants.PADDING);
        Label scoreTank1 = new Label("");
        scoreTank1.setPrefSize(100,20);
        Label scoreTank2 = new Label("");
        scoreTank2.setPrefSize(100,20);
        leftDisplay.getChildren().addAll(scoreTank1, scoreTank2);
        root.setRight(leftDisplay);

        Tank tank1 = new Tank("Denis", 100, 50, 50);
        Tank tank2 = new Tank("Pesho", 300, 100, 100);
        List<Tank> tanks = new ArrayList<>();
        booms = new ArrayList<>();
        tanks.add(tank1);
        tanks.add(tank2);
        collisionDetector = new CollisionDetector(matrix,tanks);
        tank1.setCollisionDetector(collisionDetector);
        tank2.setCollisionDetector(collisionDetector);

        InitialiseMatrix();
        bulletHandler = new ObjectHandler(collisionDetector, booms);
        InputHandler inputHandler = new InputHandler(s, tank1, tank2, hasTwoPlayers, bulletHandler);

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
                //gc.drawImage(bird,280,570);
                DrawBullets(gc);
                gc.drawImage(tank1.getImage(), tank1.getX(), tank1.getY());
                if (hasTwoPlayers) {
                    gc.drawImage(tank2.getImage(), tank2.getX(), tank2.getY());
                    scoreTank2.setText(tank2.getName() + " " + Integer.toString(tank2.getScore()));
                }
                DrawBooms(gc);
                scoreTank1.setText(tank1.getName() + " " + Integer.toString(tank1.getScore()));

                inputHandler.refresh();

            }

        }.start();

    }

    private void DrawBooms(GraphicsContext gc) {
        for (int i = 0; i < booms.size(); i++) {
            Explosion boom = booms.get(i);
            gc.drawImage(boom.getImage(), boom.getX(), boom.getY());
            if (boom.decrementFrames()){
                booms.remove(i);
            }
        }
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
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                int randomNum = random.nextInt(10);
                this.matrix[row][col] = randomNum == 0 ? 3 : 0;
            }
        }
    }

    private void DrawWalls(GraphicsContext gc, Image wall) {
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                if (matrix[row][col] != 0) {
                    gc.drawImage(wall, col * Constants.MATRIX_CELL_SIZE, row * Constants.MATRIX_CELL_SIZE);
                }
            }
        }
    }
}
