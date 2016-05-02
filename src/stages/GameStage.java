package stages;

import constants.Constants;
import game.CollisionDetector;
import game.ObjectHandler;
import input.InputHandler;
import input.MapLoader;
import input.MapReader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import objects.UI.MenuButton;
import objects.UI.ScoreLabel;
import objects.game_objects.tanks.EnemyTank;
import objects.game_objects.tanks.PlayerTank;
import objects.game_objects.tanks.Tank;
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
import objects.game_objects.Explosion;
import output.MapLevel;
import output.ScoreManager;
import utilities.ExitBox;

import java.util.ArrayList;
import java.util.List;

public class GameStage {
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
    private List<Tank> tanks;
    private int numberOfEnemiesToBeSpawn;
    private int spawnedEnemies;
    private MapLoader mapLoader;
    private InputHandler inputHandler;
    private Scene scene;
    private boolean isGameFinallyOver;

    private PlayerTank firstTank;
    private PlayerTank secondTank;

    private long lastTimeSpawn;

    public GameStage(Stage stage, Scene mainMenuScene, boolean hasTwoPlayers) {
        this.stage = stage;
        this.hasTwoPlayers = hasTwoPlayers;
        this.mainMenuScene = mainMenuScene;
        this.enemyTanks = new ArrayList<>();
        this.tanks = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.mapLoader = new MapLoader();
        this.numberOfEnemiesToBeSpawn = 10;
        this.spawnedEnemies = 0;
    }

    public void start() {
        BorderPane root = new BorderPane();

        final Canvas canvas = new Canvas(Constants.WINDOWS_HEIGHT, Constants.WINDOWS_HEIGHT);
        root.setLeft(canvas);
        gc = canvas.getGraphicsContext2D();
        scene = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);

        VBox leftDisplay = new VBox(Constants.PADDING);
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(b -> {
            savesScores();
            stage.setScene(mainMenuScene);
            stage.show();
        });
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Label scoreTank1 = new ScoreLabel("");
        Label scoreTank2 = new ScoreLabel("");
        leftDisplay.getChildren().addAll(backButton, scoreTank1, scoreTank2);
        leftDisplay.setAlignment(Pos.CENTER);
        root.setCenter(leftDisplay);

        initLevel();
        initTanks();
        initCollisionDetector(firstTank, secondTank);
        initWallImages();
        initHandlers(scene);

        stage.setScene(scene);
        stage.show();

        new AnimationTimer() {
            Image background = new Image("resources/gameplay_background.png");
            Image bird = new Image("resources/bird.png");

            @Override
            public void handle(long now) {
                if (!collisionDetector.isBirdDeath() && isTanksAlive()){
                    gc.drawImage(background, 0, 0);
                    gc.drawImage(bird, Constants.BIRD_X, Constants.BIRD_Y);
                    SpawnEnemyTanks(now, gc);
                    goToNextLevelIfCan();
                    Drawer.DrawTank(gc, firstTank);
                    if (hasTwoPlayers) {
                        Drawer.DrawTank(gc, secondTank);
                        scoreTank2.setText(secondTank.getName() + " " + Integer.toString(secondTank.getScore()));
                    }

                    Drawer.DrawWalls(gc, wallImages, matrix);
                    Drawer.DrawBullets(gc, bulletHandler);
                    Drawer.DrawExplosions(gc, explosions);
                    scoreTank1.setText(firstTank.getName() + " " + Integer.toString(firstTank.getScore()));

                    inputHandler.refresh();
                } else if(!isGameFinallyOver){
                    savesScores();
                    GameOverStage gameOver = new GameOverStage(stage, mainMenuScene);
                    gameOver.show(getTankScores());
                    isGameFinallyOver = true;
                }
            }

        }.start();

    }

    protected void initTanks() {
        firstTank = new PlayerTank("Denis", Constants.TANK_START_HEALTH, level.getFirstPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getFirstPlayerRow() * Constants.MATRIX_CELL_SIZE);
        secondTank = new PlayerTank("Pesho", Constants.TANK_START_HEALTH, level.getSecondPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getSecondPlayerRow() * Constants.MATRIX_CELL_SIZE);

        tanks.add(firstTank);
        tanks.add(secondTank);
    }

    private String getTankScores() {
        String msg = firstTank.getName() + " " + firstTank.getScore();
        if (hasTwoPlayers){
            msg += "\n" + secondTank.getName() + " " + secondTank.getScore();
        }

        return msg;
    }

    protected void initHandlers(Scene s) {
        this.bulletHandler = new ObjectHandler(collisionDetector, explosions);
        this.inputHandler = new InputHandler(s, firstTank, secondTank, hasTwoPlayers, bulletHandler);
    }

    protected void initCollisionDetector(Tank tank1, Tank tank2) {
        collisionDetector = new CollisionDetector(matrix, tanks);
        tank1.setCollisionDetector(collisionDetector);
        tank2.setCollisionDetector(collisionDetector);
    }

    protected void initLevel() {

        this.level = MapReader.readMap("maps//" + mapLoader.getNextLevelName() + ".map");
        this.matrix = level.getMatrix();
    }

    private void goToNextLevelIfCan() {
        if (spawnedEnemies >= numberOfEnemiesToBeSpawn && enemyTanks.size() == 0){
            spawnedEnemies = 0;
            initLevel();
            initCollisionDetector(firstTank, secondTank);
            initHandlers(scene);
            firstTank.goToNextLevel(level.getFirstPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getFirstPlayerRow() * Constants.MATRIX_CELL_SIZE);
            secondTank.goToNextLevel(level.getSecondPlayerCol() * Constants.MATRIX_CELL_SIZE, level.getSecondPlayerRow() * Constants.MATRIX_CELL_SIZE);
            this.lastTimeSpawn = System.nanoTime();
        }

    }

    private void SpawnEnemyTanks(long now, GraphicsContext gc) {
        if ((now - this.lastTimeSpawn) / 1_000_000_00.0 > 40 && spawnedEnemies < numberOfEnemiesToBeSpawn) {
            boolean isReadyToBeSpawn = true;
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank tempTank = enemyTanks.get(i);
                if (tempTank.getX() >= level.getEntryPointCol() * Constants.MATRIX_CELL_SIZE
                        && tempTank.getX() <= (level.getEntryPointCol() + 1) * Constants.MATRIX_CELL_SIZE
                        && tempTank.getY() >= level.getEntryPointRow() * Constants.MATRIX_CELL_SIZE
                        && tempTank.getY() <= (level.getEntryPointRow() + 1) * Constants.MATRIX_CELL_SIZE ){
                    isReadyToBeSpawn = false;
                }

            }
            if (isReadyToBeSpawn){
                this.spawnedEnemies++;
                EnemyTank enemyTank = new EnemyTank(Constants.ENEMY_TANK_START_HEALTH, level.getEntryPointCol() * Constants.MATRIX_CELL_SIZE, level.getEntryPointRow() * Constants.MATRIX_CELL_SIZE);
                enemyTank.setCollisionDetector(this.collisionDetector);
                enemyTanks.add(enemyTank);
                tanks.add(enemyTank);
                this.lastTimeSpawn = now;
            }
        }

        for (int i = 0;i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isAlive()){
                Drawer.DrawTank(gc, enemyTank);
                if (enemyTank.canShootBullet(now)) {
                    this.bulletHandler.AddBullet(enemyTank.spawnBullet());
                }
                enemyTank.moveEnemy();
            } else {
                enemyTanks.remove(i);
            }


        }

    }

    private void closeProgram() {
        ExitBox exitBox = new ExitBox(300, 100, "Exit");
        boolean isClosing = exitBox.display("Are you sure you want to close");
        if (isClosing) {
            savesScores();
            stage.close();
        }
    }

    private void savesScores() {
        ScoreManager.saveScore(firstTank.getScore(), firstTank.getName(), secondTank.getScore(), secondTank.getName());
    }

    public void initWallImages() {
        wallImages = new Image[NUMBER_OF_IMAGES];
        wallImages[0] = new Image("resources/walls/wall_ordinary.png");
        wallImages[1] = new Image("resources/walls/wall_metal.png");
        wallImages[2] = new Image("resources/walls/wall_water.png");
        wallImages[3] = new Image("resources/walls/wall_green.png");
    }

    private boolean isTanksAlive(){
        if (!hasTwoPlayers){
            return this.firstTank.isAlive();
        }

        return this.firstTank.isAlive() || this.secondTank.isAlive();
    }
}
