package main;

import constants.Constants;
import game.Game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import map_editor.MapEditor;
import objects.MenuButton;

public class Main extends Application {
    private Button onePlayerButton;
    private Button twoPlayersButton;
    private Button mapEditorButton;
    private Button settingsButton;
    private Button creditsButton;
    private Scene windows;

    @Override
    public void start(Stage primaryStage) throws Exception {
        onePlayerButton = new MenuButton("1 Player");
        twoPlayersButton = new MenuButton("2 Players");
        mapEditorButton = new MenuButton("Map Editor");
        settingsButton = new MenuButton("Settings");
        creditsButton = new MenuButton("Credits");

        onePlayerButton.setOnAction(c -> new Game(primaryStage, windows, false).start());
        twoPlayersButton.setOnAction(b -> new Game(primaryStage, windows, true).start());
        mapEditorButton.setOnAction(b -> new MapEditor(primaryStage).start());
        //TODO make two more menu pages
        //settingsButton.setOnAction(this);
        //creditsButton.setOnAction(this);

        VBox centerMenu = new VBox(Constants.PADDING);
        centerMenu.setPrefSize(Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT);
        centerMenu.setPadding(new Insets(115, 335, 115, 335));
        centerMenu.getChildren().addAll(
                onePlayerButton,
                twoPlayersButton,
                mapEditorButton,
                settingsButton,
                creditsButton);

        BorderPane borderPaneLayout = new BorderPane();
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
