package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import objects.MenuButton;

public class Main extends Application {

    Button onePlayerButton;
    Button twoPlayersButton;
    Button mapEditorButton;
    Button settingsButton;
    Button creditsButton;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        onePlayerButton = MenuButton.CreateButton("1 Player");
        twoPlayersButton = MenuButton.CreateButton("2 Players");
        mapEditorButton = MenuButton.CreateButton("Map Editor");
        settingsButton = MenuButton.CreateButton("Settings");
        creditsButton = MenuButton.CreateButton("Credits");

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
        borderPaneLayout.setCenter(centerMenu);


        Scene windows = new Scene(borderPaneLayout, 800, 600);


        primaryStage.setTitle("Tank");
        primaryStage.setScene(windows);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
