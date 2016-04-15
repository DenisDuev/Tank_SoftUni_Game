package objects;

import javafx.scene.image.Image;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class GameObject {
    protected final int UP = 0;
    protected final int DOWN = 1;
    protected final int LEFT = 2;
    protected final int RIGHT = 3;

    protected int x;
    protected int y;
    protected int direction;
    protected Image image;

    public Image getImage() {
        return image;
    }

    public GameObject(int x, int y) {
        this.direction = UP;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
