package stages;

import input.SettingsReader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import objects.UI.MenuButton;
import utilities.Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static constants.Constants.*;

public class MenuStage extends Application {

    private Scene windows;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialiseSettings();
        Button onePlayerButton = new MenuButton("1 Player");
        Button twoPlayersButton = new MenuButton("2 Players");
        Button mapEditorButton = new MenuButton("Map Editor");
        Button topScoresButton = new MenuButton("Top Scores");
        Button settingsButton = new MenuButton("Settings");
        Button creditsButton = new MenuButton("Credits");

        onePlayerButton.setOnAction(c -> new GameStage(primaryStage, this.windows, false).show());
        twoPlayersButton.setOnAction(b -> new GameStage(primaryStage, this.windows, true).show());
        mapEditorButton.setOnAction(b -> new MapEditorStage(primaryStage, this.windows).show());
        topScoresButton.setOnAction(b -> new TopScoresStage(primaryStage, this.windows).show());
        settingsButton.setOnAction(event -> new SettingsStage(primaryStage, this.windows).show());
        creditsButton.setOnAction(event -> new CreditsStage(primaryStage, this.windows).show());

        VBox centerMenu = new VBox(PADDING);
        centerMenu.setPrefSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
        centerMenu.setAlignment(Pos.CENTER);
        centerMenu.getChildren().addAll(
                onePlayerButton,
                twoPlayersButton,
                mapEditorButton,
                topScoresButton,
                settingsButton,
                creditsButton);

        BorderPane borderPaneLayout = new BorderPane();
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
        borderPaneLayout.setBackground(background);
        borderPaneLayout.setCenter(centerMenu);

        windows = new Scene(borderPaneLayout, WINDOWS_WIDTH, WINDOWS_HEIGHT);

        primaryStage.setTitle("SoftUni Tank");
        primaryStage.setScene(this.windows);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void initialiseSettings() {
        SettingsReader.loadSettings();
    }

}
