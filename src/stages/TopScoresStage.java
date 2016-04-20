package stages;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.UI.MenuButton;
import objects.UI.ScoreLabel;
import objects.UI.TopScoreLabel;
import output.ScoreManager;

/**
 * Created by Denis on 20.4.2016 ã..
 */
public class TopScoresStage {
    private Stage stage;
    private Scene scene;
    private Scene mainMenuScene;

    public TopScoresStage(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainMenuScene = mainScene;
    }

    public void show() {
        Label title = new ScoreLabel("Top 10 Scores");
        Label text = new TopScoreLabel(ScoreManager.getScores());
        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> stage.setScene(mainMenuScene));

        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(title, text, backButton);


        Background background = new Background(
                new BackgroundImage(
                        new Image("resources/menu_background.png",
                                Constants.WINDOWS_WIDTH + Constants.PADDING,
                                Constants.WINDOWS_HEIGHT + Constants.PADDING,
                                false, false),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT));
        vBox.setBackground(background);
        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
