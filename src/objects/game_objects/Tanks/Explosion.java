package objects.game_objects.Tanks;

import javafx.scene.image.Image;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class Explosion extends GameObject {
    private int frames;

    public Explosion(int x, int y) {
        super(x, y);
        this.image = new Image("resources/exploision.png");
        this.frames = 10;

    }

    public boolean decrementFrames() {
        this.frames--;
        if (this.frames <= 0){
            return true;
        }

        return false;
    }
}
