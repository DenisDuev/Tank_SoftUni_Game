package game;

import constants.Constants;
import input.InputHandler;
import input.MapReader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import objects.MenuButton;
import objects.Tanks.EnemyTank;
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
import utilities.ExitBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static final int NUMBER_OF_IMAGES = 4;

    private Stage stage;
    private Scene mainMenuScene;
    private GraphicsContext gc;
    private boolean hasTwoPlayers;
    private ObjectHandler bulletHandler;
    private List<Explosion> explosions = new ArrayList<>();
    private Image[] wallImages;
    private MapLevel level;
    private int[][] matrix;
    private List<EnemyTank> enemyTanks;
    private CollisionDetector collisionDetector;

    private long lastTime;


    public Game(Stage stage, Scene mainMenuScene, boolean hasTwoPlayers) {
        this.stage = stage;
        this.hasTwoPlayers = hasTwoPlayers;
        this.mainMenuScene = mainMenuScene;
        this.enemyTanks = new ArrayList<>();
    }

    public void start() {
        BorderPane root = new BorderPane();

        final Canvas canvas = new Canvas(Constants.WINDOWS_HEIGHT, Constants.WINDOWS_HEIGHT);
        root.setLeft(canvas);
        gc = canvas.getGraphicsContext2D();
        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);

        VBox leftDisplay = new VBox(Constants.PADDING);
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(b -> {
            stage.setScene(mainMenuScene);
            stage.show();
        });
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Label scoreTank1 = new Label("");
        scoreTank1.setPrefSize(100, 20);
        Label scoreTank2 = new Label("");
        scoreTank2.setPrefSize(100, 20);
        leftDisplay.getChildren().addAll(backButton, scoreTank1, scoreTank2);
        leftDisplay.setAlignment(Pos.CENTER);
        root.setCenter(leftDisplay);

        this.level = MapReader.readMap("maps//firstMap.map");
        this.matrix = level.getMatrix();


        Tank tank1 = new Tank("Denis", 100, level.getFirstPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getFirstPlayerRow() * Constants.MATRIX_CELL_SIZE);
        Tank tank2 = new Tank("Pesho", 300, level.getSecondPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getSecondPlayerRow() * Constants.MATRIX_CELL_SIZE);
        List<Tank> tanks = new ArrayList<>();
        explosions = new ArrayList<>();
        tanks.add(tank1);
        tanks.add(tank2);
        collisionDetector = new CollisionDetector(matrix, tanks);
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
                gc.drawImage(bird, 270, 570);
                SpawnEnemyTanks(now, gc);
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

    private void SpawnEnemyTanks(long now, GraphicsContext gc) {
        if ((now - this.lastTime)/1_000_000_00.0 > 80){
            EnemyTank enemyTank = new EnemyTank("Tank",100,30,0);
            enemyTank.setCollisionDetector(this.collisionDetector);
            enemyTanks.add(enemyTank);
            this.lastTime = now;
        }

        for (EnemyTank enemyTank : enemyTanks) {
            enemyTank.moveEnemy();
            gc.drawImage(enemyTank.getImage(), enemyTank.getX(), enemyTank.getY());
        }

    }

    private void closeProgram() {
        ExitBox exitBox = new ExitBox(300, 100, "Exit");
        boolean isClosing = exitBox.display("Are you sure you want to close");
        if (isClosing) {
            //TODO save score before leaving
            stage.close();
        }
    }

    public void initWallImages() {
        wallImages = new Image[NUMBER_OF_IMAGES];
        wallImages[0] = new Image("resources/walls/wall_ordinary.png");
        wallImages[1] = new Image("resources/walls/wall_metal.png");
        wallImages[2] = new Image("resources/walls/wall_water.png");
        wallImages[3] = new Image("resources/walls/wall_green.png");
    }
}
