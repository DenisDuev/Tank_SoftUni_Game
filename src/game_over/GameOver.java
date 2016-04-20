package game_over;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import objects.UI.MenuButton;
import objects.UI.ScoreLabel;

/**
 * Created by Denis on 20.4.2016 ã..
 */
public class GameOver {
    private Stage stage;
    private Scene mainMenuScene;

    public GameOver(Stage stage, Scene mainMenuScene) {
        this.stage = stage;
        this.mainMenuScene = mainMenuScene;
    }

    public void show(String messageScores){

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(Font.font(50));
        gameOverLabel.setTextFill(Color.RED);
        Label text = new ScoreLabel(messageScores);
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> {
            stage.setScene(mainMenuScene);
        });

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(gameOverLabel, text, backButton);

        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }
}

