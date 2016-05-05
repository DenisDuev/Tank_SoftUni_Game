package objects.game_objects;

import Interfaces.Drawable;
import Interfaces.Moveable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.game_objects.tanks.Tank;

import static constants.Constants.*;

public class Bullet extends GameObject implements Drawable, Moveable {
    public static final int VELOCITY = 5;
    public static final int IMAGE_WIGHT = 3;

    private int xVelocity;
    private int yVelocity;
    private Image image;
    private Tank parentTank;

    public Bullet(int x, int y, int direction, Tank parentTank) {
        super(x, y);
        this.direction = direction;
        this.setVelocitiesAndStartPoint();
        this.parentTank = parentTank;
        this.setImage();
    }

    private void setImage() {
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
                this.x += TANK_SIZE / 2;
                break;
            case DOWN:
                this.yVelocity = VELOCITY;
                this.x += TANK_SIZE / 2;
                this.y += TANK_SIZE;
                break;
            case LEFT:
                this.xVelocity = -VELOCITY;
                this.y += TANK_SIZE / 2;
                break;
            case RIGHT:
                this.xVelocity = VELOCITY;
                this.x += TANK_SIZE;
                this.y += TANK_SIZE / 2;
                break;
        }
    }

    public Image getImage() {
        return this.image;
    }

    public void addScoreToParentTankWallShoot(){
        this.parentTank.addScoreWallShoot();
    }

    public void addScoreToParentTankTankShoot(){
        this.parentTank.addScoreTankShoot();
    }

    public void move() {
        this.x += xVelocity;
        this.y += yVelocity;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
}
