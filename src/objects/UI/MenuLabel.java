package objects.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MenuLabel extends Label {
    public MenuLabel(String text) {
        super(text);
        this.setFont(Font.font(20));
        this.setPrefSize(250, 40);
        this.setAlignment(Pos.CENTER);
    }
}
