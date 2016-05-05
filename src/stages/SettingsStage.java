package stages;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SettingsStage extends BasicStage {

    public SettingsStage(Stage stage, Scene mainMenuScene) {
        super(stage, mainMenuScene);
    }

    @Override
    public void show() {
        TextField textField = new TextField();
        textField.setPrefSize(200, 40);
        textField.setPrefColumnCount(3);

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

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(background);
        vBox.setPrefSize(300, 600);
        vBox.getChildren().addAll(textField);

        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
