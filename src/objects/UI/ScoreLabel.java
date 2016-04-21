package objects.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Created by Denis on 20.4.2016 ã..
 */
public class ScoreLabel extends Label {
    public ScoreLabel(String text) {
        super(text);
        this.setFont(Font.font(20));
        this.setPrefSize(250, 70);
        this.setAlignment(Pos.CENTER);
    }
}
