package stages;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.UI.MenuButton;

public class CreditsStage extends BasicStage {
    public CreditsStage(Stage stage, Scene mainMenuScene) {
        super(stage, mainMenuScene);
    }

    @Override
    public void show(){
        Text creditsDenis = new Text("Denis Duev - from Krupnik, graduated from PMG Blagoevgrad with\na specialty in mathematics and informatics works as a creator of online\nvideo lessons on mathematics for the internet site SuperUrok.com. has\nexperience with C# and Java, has certificates for accomplishing courses\nC# Basics, Data structures and Java Fundamentalsat SoftUni,\nstudentof SoftUni and Sofia University");
        creditsDenis.setFont(Font.font(18));

        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> stage.setScene(mainMenuScene));

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
        vBox.getChildren().addAll(creditsDenis, backButton);
        vBox.setBackground(background);

        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
