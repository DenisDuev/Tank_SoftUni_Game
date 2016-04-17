package map_editor;

import constants.Constants;
import game.Game;
import game.output.Drawer;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.MenuButton;

/**
 * Created by Denis on 16.4.2016 ã..
 */
public class MapEditor {
    private Stage stage;
    private GraphicsContext gc;
    private static int[][] matrix = new int[Constants.MATRIX_ROWS][Constants.MATRIX_COLS];
    private Image[] wallImages;

    public MapEditor(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        BorderPane root = new BorderPane();

        final Canvas canvas = new Canvas(Constants.WINDOWS_HEIGHT, Constants.WINDOWS_HEIGHT);
        root.setLeft(canvas);

        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        gc = canvas.getGraphicsContext2D();

        Button button = new MenuButton("SAVE");

        VBox leftMenu = new VBox(Constants.PADDING);
        leftMenu.getChildren().add(button);
        leftMenu.setPrefSize(Constants.BOARD_PADDING, Constants.BOARD_SIZE);
        leftMenu.setPadding(new Insets(10, 10, 30, 30));
        root.setRight(leftMenu);
        initWallImages();
        s.setOnMouseClicked(event -> {
            if (event.getX() < Constants.BOARD_SIZE) {
                matrix[(int) event.getX() / Constants.MATRIX_CELL_SIZE][(int) event.getY() / Constants.MATRIX_CELL_SIZE] =
                        ++matrix[(int) event.getX() / Constants.MATRIX_CELL_SIZE][(int) event.getY() / Constants.MATRIX_CELL_SIZE] % 5;
            }
        });

        stage.setScene(s);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Drawer.drawCellsMapEdit(gc, wallImages, matrix);
            }
        }.start();
    }


    public void initWallImages() {
        wallImages = new Image[5];
        wallImages[0] = new Image("resources/empty_cell.png");
        wallImages[1] = new Image("resources/walls/wall_ordinary.png");
        wallImages[2] = new Image("resources/walls/wall_metal.png");
        wallImages[3] = new Image("resources/walls/wall_water.png");
        wallImages[4] = new Image("resources/walls/wall_green.png");
    }
}

