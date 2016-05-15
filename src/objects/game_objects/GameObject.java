package objects.game_objects;

import javafx.scene.image.Image;

public abstract class GameObject {
    protected final static int UP = 0;
    protected final static int DOWN = 1;
    protected final static int LEFT = 2;
    protected final static int RIGHT = 3;

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
