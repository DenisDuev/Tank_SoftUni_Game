package main;

import constants.Constants;
import game.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import map_editor.MapEditor;
import objects.MenuButton;

public class Main extends Application {

    Button onePlayerButton;
    Button twoPlayersButton;
    Button mapEditorButton;
    Button settingsButton;
    Button creditsButton;
    Scene windows;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        onePlayerButton = new MenuButton("1 Player");
        twoPlayersButton = new MenuButton("2 Players");
        mapEditorButton = new MenuButton("Map Editor");
        settingsButton = new MenuButton("Settings");
        creditsButton = new MenuButton("Credits");

        onePlayerButton.setOnAction(c -> new Game(primaryStage, false).start());
        twoPlayersButton.setOnAction(b -> new Game(primaryStage, true).start());
        mapEditorButton.setOnAction(b -> new MapEditor(primaryStage).start());
        //TODO make two more menu pages
        //settingsButton.setOnAction(this);
        //creditsButton.setOnAction(this);

        VBox centerMenu = new VBox();
        centerMenu.setPadding(new Insets(110, 325, 110, 325));
        centerMenu.setSpacing(10);
        centerMenu.getChildren().addAll(
                onePlayerButton,
                twoPlayersButton,
                mapEditorButton,
                settingsButton,
                creditsButton);

        BorderPane borderPaneLayout = new BorderPane();
        Background background = new Background(
                new BackgroundImage(
                        new Image("resources/menu_background.png", Constants.WINDOWS_WIDTH + 10, Constants.WINDOWS_HEIGHT + 10, false, false),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT));
        borderPaneLayout.setBackground(background);
        borderPaneLayout.setCenter(centerMenu);


        windows = new Scene(borderPaneLayout, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT);


        primaryStage.setTitle("SoftUni Tank");
        primaryStage.setScene(windows);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
