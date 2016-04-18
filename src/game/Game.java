package game;

import constants.Constants;
import input.InputHandler;
import input.MapReader;
import output.Drawer;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Explosion;
import objects.Tanks.Tank;
import output.MapLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int NUMBER_OF_IMAGES = 4;

    private Stage stage;
    private GraphicsContext gc;
    private boolean hasTwoPlayers;
    private ObjectHandler bulletHandler;
    private List<Explosion> explosions = new ArrayList<>();
    private Image[] wallImages;
    private MapLevel level;
    private int[][] matrix;


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
        scoreTank1.setPrefSize(100, 20);
        Label scoreTank2 = new Label("");
        scoreTank2.setPrefSize(100, 20);
        leftDisplay.getChildren().addAll(scoreTank1, scoreTank2);
        root.setRight(leftDisplay);

        this.level = MapReader.readMap("maps//firstMap.map");
        this.matrix = level.getMatrix();


        Tank tank1 = new Tank("Denis", 100, level.getFirstPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getFirstPlayerRow() * Constants.MATRIX_CELL_SIZE);
        Tank tank2 = new Tank("Pesho", 300, level.getSecondPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getSecondPlayerRow() * Constants.MATRIX_CELL_SIZE);
        List<Tank> tanks = new ArrayList<>();
        explosions = new ArrayList<>();
        tanks.add(tank1);
        tanks.add(tank2);
        CollisionDetector collisionDetector = new CollisionDetector(matrix, tanks);
        tank1.setCollisionDetector(collisionDetector);
        tank2.setCollisionDetector(collisionDetector);


        initWallImages();
        bulletHandler = new ObjectHandler(collisionDetector, explosions);
        InputHandler inputHandler = new InputHandler(s, tank1, tank2, hasTwoPlayers, bulletHandler);

        stage.setScene(s);

        stage.show();

        new AnimationTimer() {
            Image background = new Image("resources/gameplay_background.png");
            Image bird = new Image("resources/bird.png");

            @Override
            public void handle(long now) {

                gc.drawImage(background, 0, 0);
                gc.drawImage(bird, 280, 570);
                Drawer.DrawTank(gc, tank1);
                if (hasTwoPlayers) {
                    Drawer.DrawTank(gc, tank2);
                    scoreTank2.setText(tank2.getName() + " " + Integer.toString(tank2.getScore()));
                }
                Drawer.DrawWalls(gc, wallImages, matrix);
                Drawer.DrawBullets(gc, bulletHandler);
                Drawer.DrawExplosions(gc, explosions);
                scoreTank1.setText(tank1.getName() + " " + Integer.toString(tank1.getScore()));

                inputHandler.refresh();

            }

        }.start();

    }
    public void initWallImages() {
        wallImages = new Image[NUMBER_OF_IMAGES];
        wallImages[0] = new Image("resources/walls/wall_ordinary.png");
        wallImages[1] = new Image("resources/walls/wall_metal.png");
        wallImages[2] = new Image("resources/walls/wall_water.png");
        wallImages[3] = new Image("resources/walls/wall_green.png");
    }

    //TODO fix matrix generation
    private void initialiseMatrix() {
        Random random = new Random();
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                int randomNum = random.nextInt(13);
                if (col % 3 == 0)
                this.matrix[row][col] = randomNum;
            }
        }
    }


}
