package map_editor;

import constants.Constants;
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

    private Image wall = new Image("resources/walls/wall_ordinary.png");
    private Image emptySell = new Image("resources/empty_cell.png");

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

        s.setOnMouseClicked(event -> {
            if (event.getX() < Constants.BOARD_SIZE) {
                matrix[(int) event.getX() / Constants.MATRIX_CELL_SIZE][(int) event.getY() / Constants.MATRIX_CELL_SIZE]++;
            }
        });

        stage.setScene(s);
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                DrawMatrix(gc);

            }
        }.start();
    }

    private void DrawMatrix(GraphicsContext gc) {

        for (int i = 0; i < Constants.MATRIX_ROWS; i++) {
            for (int j = 0; j < Constants.MATRIX_COLS; j++) {
                if (this.matrix[i][j] % 2 == 1) {
                    gc.drawImage(wall, i * Constants.MATRIX_CELL_SIZE, j * Constants.MATRIX_CELL_SIZE);
                } else {
                    gc.drawImage(emptySell, i * Constants.MATRIX_CELL_SIZE, j * Constants.MATRIX_CELL_SIZE);
                }
            }
        }
    }
}
