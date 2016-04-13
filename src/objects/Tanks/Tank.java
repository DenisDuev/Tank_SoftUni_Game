package objects.Tanks;

import javafx.scene.image.Image;

/**
 * Created by Denis on 13.4.2016 ã..
 */
public class Tank {
    enum posittion {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private String name;
    private int health;
    private Image upImage;
    private Image downImage;
    private Image leftImage;
    private Image rightImage;

    private int x;
    private int y;

    public Tank(String name, int health, int x, int y) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
        this.upImage = new Image("resources/unnamed.png");
    }

    public void move(int xAdd, int yAdd) {
        //TODO check for collison
        this.x += xAdd;
        this.y += yAdd;
    }

    public Image getUpImage() {
        return upImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
