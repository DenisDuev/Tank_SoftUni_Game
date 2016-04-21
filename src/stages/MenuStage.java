package stages;

import constants.Constants;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import objects.UI.MenuButton;

public class MenuStage extends Application {
    private Button onePlayerButton;
    private Button twoPlayersButton;
    private Button mapEditorButton;
    private Button settingsButton;
    private Button creditsButton;
    private Button topScoresButton;
    private Scene windows;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.onePlayerButton = new MenuButton("1 Player");
        this.twoPlayersButton = new MenuButton("2 Players");
        this.mapEditorButton = new MenuButton("Map Editor");
        this.topScoresButton = new MenuButton("Top Scores");
        this.settingsButton = new MenuButton("Settings");
        this.creditsButton = new MenuButton("Credits");

        this.onePlayerButton.setOnAction(c -> new GameStage(primaryStage, windows, false).start());
        this.twoPlayersButton.setOnAction(b -> new GameStage(primaryStage, windows, true).start());
        this.mapEditorButton.setOnAction(b -> new MapEditorStage(primaryStage, windows).start());
        this.topScoresButton.setOnAction(b -> new TopScoresStage(primaryStage, windows).show());
        //TODO make two more menu pages
        //settingsButton.setOnAction(this);
        creditsButton.setOnAction(event -> new CreditsStage(primaryStage,windows).show() );

        VBox centerMenu = new VBox(Constants.PADDING);
        centerMenu.setPrefSize(Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT);
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
