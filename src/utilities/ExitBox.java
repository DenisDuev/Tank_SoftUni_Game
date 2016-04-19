package utilities;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by Denis on 19.4.2016 ã..
 */
public class ExitBox extends AlertBox {
    private boolean closeTheProgram;

    public ExitBox(int width, int height, String title) {
        super(width, height, title);
        closeTheProgram = false;
    }

    @Override
    public boolean display(String message) {
        Stage windows = initStage();

        Label label = new Label(message);
        label.setFont(Font.font(20));
        HBox labelLayout = new HBox(Constants.PADDING);
        labelLayout.getChildren().addAll(label);
        labelLayout.setAlignment(Pos.CENTER);

        Button buttonYes = new Button("Yes");
        Button buttonNo = new Button("No");

        buttonYes.setOnMouseClicked(b -> {
            closeTheProgram = true;
            windows.close();
        });
        buttonNo.setOnMouseClicked(b ->{
            this.closeTheProgram = false;
            windows.close();
        });

        HBox buttonsLayout = new HBox(Constants.PADDING);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.getChildren().addAll(buttonYes, buttonNo);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(labelLayout, buttonsLayout);

        Scene scene = new Scene(layout);
        windows.setScene(scene);
        windows.showAndWait();

        return this.closeTheProgram;
    }


}
