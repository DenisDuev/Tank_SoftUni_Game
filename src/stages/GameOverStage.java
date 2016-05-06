package stages;

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
import objects.UI.MenuLabel;

public class GameOverStage extends BasicStage {

    public GameOverStage(Stage stage, Scene mainMenuScene) {
        super(stage, mainMenuScene);
    }

    @Override
    public void show(String messageScores){

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(Font.font(50));
        gameOverLabel.setTextFill(Color.RED);
        Label text = new MenuLabel(messageScores);
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> stage.setScene(mainMenuScene));

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(gameOverLabel, text, backButton);

        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }

    @Override
    public void show() {

    }
}

