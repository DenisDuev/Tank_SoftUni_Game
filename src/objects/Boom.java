package objects;

import javafx.scene.image.Image;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class Boom extends GameObject {
    private int frames;

    public Boom(int x, int y) {
        super(x, y);
        this.image = new Image("resources/boom.png");
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
