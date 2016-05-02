package objects.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class TopScoreLabel extends Label {
    public TopScoreLabel(String text) {
        super(text);
        this.setFont(Font.font(20));
        this.setPrefSize(300, 300);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-border-color:black; -fx-background-color: white;");
    }
}
