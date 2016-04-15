package main;

import constants.Constants;
import game.Game;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import objects.MenuButton;

public class Main extends Application implements EventHandler {

    Button onePlayerButton;
    Button twoPlayersButton;
    Button mapEditorButton;
    Button settingsButton;
    Button creditsButton;
    Scene windows;

    @Override
    public void start(Stage primaryStage)  throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        onePlayerButton = new MenuButton("1 Player");
        twoPlayersButton = new MenuButton("2 Players");
        mapEditorButton = new MenuButton("Map Editor");
        settingsButton = new MenuButton("Settings");
        creditsButton = new MenuButton("Credits");

        onePlayerButton.setOnAction(c -> new Game(primaryStage, false).start());
        twoPlayersButton.setOnAction(b -> new Game(primaryStage, true).start());
        mapEditorButton.setOnAction(this);
        settingsButton.setOnAction(this);
        creditsButton.setOnAction(this);

        VBox centerMenu = new VBox();
        centerMenu.setPadding(new Insets(110,325,110,325));
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
                                            new Image("resources/menu_background.png", Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, false, true),
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

    @Override
    public void handle(Event event) {
        EventTarget target = event.getTarget();
        if (target == mapEditorButton) {
            System.out.println("map edit btn");
        } else if (target == settingsButton) {
            System.out.println("settings btn");
        } else if (target == creditsButton) {
            System.out.println("credit btn");
        }
    }
}
