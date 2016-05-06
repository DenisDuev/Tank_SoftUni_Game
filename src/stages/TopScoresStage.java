package stages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.UI.MenuButton;
import objects.UI.MenuLabel;
import objects.UI.TopScoreLabel;
import output.ScoreManager;

import static constants.Constants.*;

public class TopScoresStage extends BasicStage {
    public TopScoresStage(Stage stage, Scene mainScene) {
        super(stage, mainScene);
    }

    @Override
    public void show() {
        Label title = new MenuLabel("Top 10 Scores");
        Label text = new TopScoreLabel(ScoreManager.getScores());
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> stage.setScene(mainMenuScene));

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(title, text, backButton);


        Background background = new Background(
                new BackgroundImage(
                        new Image("resources/menu_background.png",
                                WINDOWS_WIDTH + PADDING,
                                WINDOWS_HEIGHT + PADDING,
                                false, false),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT));
        vBox.setBackground(background);
        Scene scene = new Scene(vBox, WINDOWS_WIDTH, WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
