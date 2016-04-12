package objects;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class MenuButton extends Button{
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 60;

    public MenuButton(String text) {
        super(text);

        this.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        this.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        this.setTextFill(Color.WHITE);
        this.setStyle("-fx-font-size:22;" +
            "-fx-background-radius: 1em;" +
            "-fx-background-color: black");

    }
}
