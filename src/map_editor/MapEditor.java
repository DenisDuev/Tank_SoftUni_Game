package map_editor;

import constants.Constants;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.Tanks.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 16.4.2016 ã..
 */
public class MapEditor {
    private Stage stage;
    private GraphicsContext gc;
    private static int[][] matrix = new int[Constants.MATRIX_ROWS][Constants.MATRIX_COLS];
    Image background = new Image("resources/gameplay_background.png");
    Image wall = new Image("resources/wall_ordinary.png");
    Image emptySell = new Image("resources/empty_cell.png");

    public MapEditor(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        Group root = new Group();

        final Canvas canvas = new Canvas(Constants.WINDOWS_HEIGHT, Constants.WINDOWS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Scene s = new Scene(root, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        s.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                matrix[(int) event.getX() / Constants.MATRIX_CELL_SIZE][(int) event.getY() / Constants.MATRIX_CELL_SIZE]++;
            }
        });

        //InitialiseMatrix();

        root.getChildren().add(canvas);
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
