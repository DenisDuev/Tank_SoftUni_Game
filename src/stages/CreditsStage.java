package stages;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.UI.MenuButton;
import objects.UI.ScoreLabel;

/**
 * Created by MartinLachev on 4/21/2016.
 */
public class CreditsStage {
    private Stage stage;
    private Scene mainMenuScene;

    public CreditsStage(Stage stage, Scene mainMenuScene) {
        this.stage = stage;
        this.mainMenuScene = mainMenuScene;
    }

    public void show(){


        Text creditsDenis = new Text("Denis Duev - from Krupnik, graduated from PMG Blagoevgrad with a specialty in mathematics and informatics \n works as a creator of online video lessons on mathematics for the internet site SuperUrok.com. has experience \n with C# and Java, has certificates for accomplishing courses C# Basics, Data structures and Java Fundamentals \n at SoftUni, student of SoftUni and Sofia University");
        Text creditsMarto = new Text("Martin Lachev - from Sofia, unfortunatelly curently studing in UNWE and fortunatelly in Softuni  ");


        Button backButton = new MenuButton("Back");
        backButton.setOnMouseClicked(e -> {
            stage.setScene(mainMenuScene);
        });

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(creditsDenis,creditsMarto, backButton);

        Scene scene = new Scene(vBox, Constants.WINDOWS_WIDTH, Constants.WINDOWS_HEIGHT, Color.BLACK);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
