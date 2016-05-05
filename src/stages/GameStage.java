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

import static constants.Constants.*;

public class GameStage extends BasicStage {
    private static final int NUMBER_OF_IMAGES = 4;

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
    private Label scoreTank1;
    private Label scoreTank2;

    private long lastTimeSpawn;

    public GameStage(Stage stage, Scene mainMenuScene, boolean hasTwoPlayers) {
        super(stage, mainMenuScene);
        this.hasTwoPlayers = hasTwoPlayers;
        this.enemyTanks = new ArrayList<>();
        this.tanks = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.mapLoader = new MapLoader();
        this.numberOfEnemiesToBeSpawn = 10;
        this.spawnedEnemies = 0;
    }

    @Override
    public void show() {
        BorderPane root = new BorderPane();

        final Canvas canvas = new Canvas(WINDOWS_HEIGHT, WINDOWS_HEIGHT);
        root.setLeft(canvas);
        gc = canvas.getGraphicsContext2D();
        scene = new Scene(root, WINDOWS_WIDTH, WINDOWS_HEIGHT, Color.BLACK);

        VBox leftDisplay = new VBox(Constants.PADDING);
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(b -> {
            savesScores();
            this.stage.setScene(this.mainMenuScene);
            this.stage.show();
        });
        this.stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        this.scoreTank1 = new ScoreLabel("");
        this.scoreTank2 = new ScoreLabel("");
        leftDisplay.getChildren().addAll(backButton, scoreTank1, scoreTank2);
        leftDisplay.setAlignment(Pos.CENTER);
        root.setCenter(leftDisplay);

        initLevel();
        initTanks();
        initCollisionDetector(firstTank, secondTank);
        initWallImages();
        initHandlers(scene);

        this.stage.setScene(scene);
        this.stage.show();

        new AnimationTimer() {
            Image background = new Image("resources/gameplay_background.png");
            Image bird = new Image("resources/bird.png");

            @Override
            public void handle(long now) {
                if (!collisionDetector.isBirdDeath() && isTanksAlive()){
                    Drawer.drawBackground(gc,background);
                    Drawer.drawBird(gc,bird);
                    spawnEnemyTanks(now, gc);
                    goToNextLevelIfCan();
                    Drawer.drawTank(gc, firstTank);
                    if (hasTwoPlayers) {
                        Drawer.drawTank(gc, secondTank);
                        scoreTank2.setText(secondTank.getName() + " " + Integer.toString(secondTank.getScore()));
                    }

                    Drawer.drawWalls(gc, wallImages, matrix);
                    Drawer.drawBullets(gc, bulletHandler);
                    Drawer.drawExplosions(gc, explosions);
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
        this.firstTank = new PlayerTank("Denis", TANK_START_HEALTH, level.getFirstPlayerCol() * MATRIX_CELL_SIZE, level.getFirstPlayerRow() * MATRIX_CELL_SIZE);
        this.secondTank = new PlayerTank("Pesho", TANK_START_HEALTH, level.getSecondPlayerCol() * MATRIX_CELL_SIZE, level.getSecondPlayerRow() * MATRIX_CELL_SIZE);

        this.tanks.add(this.firstTank);
        if(this.hasTwoPlayers){
            this.tanks.add(this.secondTank);
        }
    }

    private String getTankScores() {
        StringBuilder msg = new StringBuilder();
        msg.append(this.firstTank.getName())
                .append(" ")
                .append(this.firstTank.getScore());
        if (this.hasTwoPlayers){
            msg.append("\n")
                    .append(this.secondTank.getName())
                    .append(" ")
                    .append(this.secondTank.getScore());
        }

        return msg.toString();
    }

    protected void initHandlers(Scene s) {
        this.bulletHandler = new ObjectHandler(this.collisionDetector, this.explosions);
        this.inputHandler = new InputHandler(s, this.firstTank, this.secondTank, this.hasTwoPlayers, this.bulletHandler);
    }

    protected void initCollisionDetector(Tank tank1, Tank tank2) {
        this.collisionDetector = new CollisionDetector(this.matrix, this.tanks);
        tank1.setCollisionDetector(this.collisionDetector);
        tank2.setCollisionDetector(this.collisionDetector);
    }

    protected void initLevel() {

        this.level = MapReader.readMap("maps//" + this.mapLoader.getNextLevelName() + ".map");
        this.matrix = level.getMatrix();
    }

    private void goToNextLevelIfCan() {
        if (this.spawnedEnemies >= this.numberOfEnemiesToBeSpawn && this.enemyTanks.size() == 0){
            this.spawnedEnemies = 0;
            initLevel();
            initCollisionDetector(this.firstTank, this.secondTank);
            initHandlers(this.scene);
            this.firstTank.goToNextLevel(this.level.getFirstPlayerCol() * MATRIX_CELL_SIZE, level.getFirstPlayerRow() * MATRIX_CELL_SIZE);
            this.secondTank.goToNextLevel(this.level.getSecondPlayerCol() * MATRIX_CELL_SIZE, level.getSecondPlayerRow() * MATRIX_CELL_SIZE);
            this.lastTimeSpawn = System.nanoTime();
        }

    }

    private void spawnEnemyTanks(long now, GraphicsContext gc) {
        if ((now - this.lastTimeSpawn) / 1_000_000_00.0 > 40 && this.spawnedEnemies < this.numberOfEnemiesToBeSpawn) {
            boolean isReadyToBeSpawn = true;
            for (EnemyTank tempTank : this.enemyTanks) {
                if (isEntryPointFree(tempTank)) {
                    isReadyToBeSpawn = false;
                }

            }
            if (isReadyToBeSpawn){
                this.spawnedEnemies++;
                EnemyTank enemyTank = new EnemyTank(ENEMY_TANK_START_HEALTH, level.getEntryPointCol() * MATRIX_CELL_SIZE, level.getEntryPointRow() * MATRIX_CELL_SIZE);
                enemyTank.setCollisionDetector(this.collisionDetector);
                this.enemyTanks.add(enemyTank);
                this.tanks.add(enemyTank);
                this.lastTimeSpawn = now;
            }
        }

        for (int i = 0;i < this.enemyTanks.size(); i++) {
            EnemyTank enemyTank = this.enemyTanks.get(i);
            if (enemyTank.isAlive()){
                Drawer.drawTank(gc, enemyTank);
                if (enemyTank.canShootBullet(now)) {
                    this.bulletHandler.addBullet(enemyTank.spawnBullet());
                }
                enemyTank.moveEnemy();
            } else {
                this.enemyTanks.remove(i);
            }
        }

    }

    private boolean isEntryPointFree(EnemyTank tempTank) {
        return tempTank.getX() >= this.level.getEntryPointCol() * MATRIX_CELL_SIZE
                && tempTank.getX() <= (this.level.getEntryPointCol() + 1) * MATRIX_CELL_SIZE
                && tempTank.getY() >= this.level.getEntryPointRow() * MATRIX_CELL_SIZE
                && tempTank.getY() <= (this.level.getEntryPointRow() + 1) * MATRIX_CELL_SIZE;
    }

    private void closeProgram() {
        ExitBox exitBox = new ExitBox(300, 100, "Exit");
        boolean isClosing = exitBox.display("Are you sure you want to close");
        if (isClosing) {
            savesScores();
            this.stage.close();
        }
    }

    private void savesScores() {
        ScoreManager.saveScore(this.firstTank.getScore(), this.firstTank.getName(), this.secondTank.getScore(), this.secondTank.getName());
    }

    public void initWallImages() {
        this.wallImages = new Image[NUMBER_OF_IMAGES];
        this.wallImages[0] = new Image("resources/walls/wall_ordinary.png");
        this.wallImages[1] = new Image("resources/walls/wall_metal.png");
        this.wallImages[2] = new Image("resources/walls/wall_water.png");
        this.wallImages[3] = new Image("resources/walls/wall_green.png");
    }

    private boolean isTanksAlive(){
        if (!this.hasTwoPlayers){
            return this.firstTank.isAlive();
        }

        return this.firstTank.isAlive() || this.secondTank.isAlive();
    }
}
