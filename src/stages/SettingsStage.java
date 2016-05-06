package stages;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.UI.MenuButton;
import objects.UI.MenuLabel;
import output.SettingsWriter;
import utilities.Settings;

public class SettingsStage extends BasicStage {
    private TextField firstPlayerName;
    private TextField secondPlayerName;

    public SettingsStage(Stage stage, Scene mainMenuScene) {
        super(stage, mainMenuScene);
    }

    @Override
    public void show() {
        MenuLabel firstTankNameLabel = new MenuLabel("First Player Name:");
        this.firstPlayerName = new TextField(Settings.getFirstPlayerName());
        this.firstPlayerName.setMaxSize(150, 30);

        MenuLabel secondTankNameLabel = new MenuLabel("Second Player Name:");
        this.secondPlayerName= new TextField(Settings.getSecondPlayerName());
        this.secondPlayerName.setMaxSize(150, 30);

        Button soundButton = new MenuButton("Sound");
        soundButton.setOnMouseClicked(e-> {
            if (Settings.isEnableSound()){
                soundButton.setStyle("-fx-font-size:22;" +
                        "-fx-background-radius: 1em;" +
                        "-fx-background-color: green");
                Settings.setEnableSound(false);
            } else {
                soundButton.setStyle("-fx-font-size:22;" +
                        "-fx-background-radius: 1em;" +
                        "-fx-background-color: red");
                Settings.setEnableSound(true);
            }
        });


        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> stage.setScene(mainMenuScene));

        Button saveButton = new MenuButton("Save");
        saveButton.setOnMouseClicked(e -> saveSettings());

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
        vBox.getChildren().addAll(firstTankNameLabel,
                firstPlayerName,
                secondTankNameLabel,
                secondPlayerName,
                soundButton,
                saveButton,
                backButton);

        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void saveSettings() {
        Settings.setFirstPlayerName(this.firstPlayerName.getText());
        Settings.setSecondPlayerName(this.secondPlayerName.getText());
        SettingsWriter.saveSettings();
    }
}
