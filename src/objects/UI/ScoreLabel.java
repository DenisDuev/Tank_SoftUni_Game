package objects.UI;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Created by Denis on 20.4.2016 ã..
 */
public class ScoreLabel extends Label {
    public ScoreLabel(String text) {
        super(text);
        this.setFont(Font.font(25));
        this.setPrefSize(100, 50);
    }
}
