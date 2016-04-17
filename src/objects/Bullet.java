package objects;

import constants.Constants;
import javafx.scene.image.Image;
import objects.Tanks.Tank;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class Bullet extends GameObject {
    public static final int VELOCITY = 5;
    public static final int IMAGE_WIDHT = 3;
    public static final int IMAGE_HEIGHT = 8;

    private int xVelocity;
    private int yVelocity;
    private Image image;
    private Tank parentTank;

    public Bullet(int x, int y, int direction, Tank parentTank) {
        super(x, y);
        this.direction = direction;
        this.setVelocitiesAndStartPoint();
        this.parentTank = parentTank;
        SetImage();
    }

    private void SetImage() {
        switch (this.direction) {
            case UP:
                this.image = new Image("resources/bullets/bullet_up.png");
                break;
            case DOWN:
                this.image = new Image("resources/bullets/bullet_down.png");
                break;
            case LEFT:
                this.image = new Image("resources/bullets/bullet_left.png");
                break;
            case RIGHT:
                this.image = new Image("resources/bullets/bullet_right.png");
                break;
        }
    }

    public void setVelocitiesAndStartPoint() {
        switch (this.direction) {
            case UP:
                this.yVelocity = -VELOCITY;
                this.x += (Constants.TANK_SIZE) / 2;
                break;
            case DOWN:
                this.yVelocity = VELOCITY;
                this.x += (Constants.TANK_SIZE) / 2;
                this.y += Constants.TANK_SIZE;
                break;
            case LEFT:
                this.xVelocity = -VELOCITY;
                this.y += (Constants.TANK_SIZE) / 2;
                break;
            case RIGHT:
                this.xVelocity = VELOCITY;
                this.x += Constants.TANK_SIZE;
                this.y += (Constants.TANK_SIZE) / 2;
                break;
        }
    }

    public Image getImage() {
        return this.image;
    }

    public Tank getParentTank() {
        return parentTank;
    }

    public void Move() {
        this.x += xVelocity;
        this.y += yVelocity;
    }
}
