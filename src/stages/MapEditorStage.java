package stages;

import javafx.geometry.Pos;
import output.Drawer;
import output.MapWriter;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.UI.MenuButton;
import utilities.AlertBox;
import utilities.ExitBox;

import static constants.Constants.*;

public class MapEditorStage extends BasicStage {
    private GraphicsContext gc;
    private static int[][] matrix = new int[MATRIX_ROWS][MATRIX_COLS];
    private Image[] wallImages;
    private TextField textField;

    public MapEditorStage(Stage stage, Scene mainMenuScene) {
        super(stage, mainMenuScene);
    }

    @Override
    public void show() {
        BorderPane root = new BorderPane();

        final Canvas canvas = new Canvas(WINDOWS_HEIGHT, WINDOWS_HEIGHT);
        root.setLeft(canvas);

        Scene s = new Scene(root, WINDOWS_WIDTH, WINDOWS_HEIGHT, Color.BLACK);
        gc = canvas.getGraphicsContext2D();

        Button button = new MenuButton("SAVE");
        textField = new TextField();

        button.setOnAction(b -> saveMap());
        stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(b -> {
            stage.setScene(mainMenuScene);
            stage.show();
        });

        VBox leftMenu = new VBox(PADDING);
        leftMenu.setAlignment(Pos.CENTER);
        leftMenu.getChildren().addAll(backButton, button, textField);
        leftMenu.setPrefSize(BOARD_PADDING, BOARD_SIZE);
        leftMenu.setPadding(new Insets(10, 10, 30, 30));
        root.setCenter(leftMenu);

        initWallImages();
        s.setOnMouseClicked(event -> {
            if (event.getX() < BOARD_SIZE) {
                matrix[(int) event.getY() / MATRIX_CELL_SIZE][(int) event.getX() / MATRIX_CELL_SIZE] =
                        ++matrix[(int) event.getY() / MATRIX_CELL_SIZE][(int) event.getX() / MATRIX_CELL_SIZE] % 5;
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

    private void saveMap() {
        String mapName = textField.getText();
        AlertBox alertBox = new AlertBox(300, 100, "Tank");
        if (MapWriter.addMap(matrix, mapName)) {
            alertBox.display("Your map is saved");
        } else {
            alertBox.display("Please enter valid map name");
        }
    }

    public void initWallImages() {
        wallImages = new Image[5];
        wallImages[0] = new Image("resources/empty_cell.png");
        wallImages[1] = new Image("resources/walls/wall_ordinary.png");
        wallImages[2] = new Image("resources/walls/wall_metal.png");
        wallImages[3] = new Image("resources/walls/wall_water.png");
        wallImages[4] = new Image("resources/walls/wall_green.png");
    }

    private void closeProgram() {
        ExitBox exitBox = new ExitBox(300, 100, "Exit");
        boolean isClosing = exitBox.display("Are you sure you want to close");
        if (isClosing) {
            stage.close();
        }
    }
}

