package utilities;

import constants.Constants;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    private int width;
    private int height;
    private String title;

    public AlertBox(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public boolean display(String message){
        Stage window = initStage();

        Label label = new Label(message);
        label.setFont(Font.font(20));
        HBox layout = new HBox(Constants.PADDING);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return true;
    }

    protected Stage initStage() {
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(this.title);
        window.setResizable(false);
        window.setWidth(this.width);
        window.setHeight(this.height);
        return window;
    }
}
