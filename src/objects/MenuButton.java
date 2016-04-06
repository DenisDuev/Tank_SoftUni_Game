package objects;

import java.awt.*;
import javafx.scene.control.Button;

public class MenuButton{
    private static final int BUTTON_WIDTH = 140;
    private static final int BUTTON_HEIGHT = 60;


    public static Button CreateButton(String text){
        Button button = new Button(text);
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        return button;
    }
}
