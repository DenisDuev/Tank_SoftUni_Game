package objects.game_objects.tanks;

import Interfaces.CollisionDetectionable;
import Interfaces.Drawable;
import game.CollisionDetector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.game_objects.Bullet;
import objects.game_objects.GameObject;

public abstract class Tank extends GameObject implements Drawable, CollisionDetectionable {
    protected int health;
    private boolean isAlive;
    protected Image upImage;
    protected Image downImage;
    protected Image leftImage;
    protected Image rightImage;
    protected Image currentImage;
    protected int tankDamage;
    protected CollisionDetector collisionDetector;


    public Tank(int health, int x, int y)  {
        super(x,y);
        this.health = health;
        this.isAlive = true;
        this.initImages();
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    public void die(){
        this.isAlive = false;
    }

    protected void initImages() {
        this.upImage = new Image("resources/tanks/silver_tank/silver_tank_up.png");
        this.downImage = new Image("resources/tanks/silver_tank/silver_tank_down.png");
        this.leftImage = new Image("resources/tanks/silver_tank/silver_tank_left.png");
        this.rightImage = new Image("resources/tanks/silver_tank/silver_tank_right.png");
        this.currentImage = new Image("resources/tanks/silver_tank/silver_tank_up.png");
    }

    public void move(int xAdd, int yAdd) {
        if (this.isAlive()){
            checkDirection(xAdd, yAdd);

            if (collisionDetector.shouldTankMove(this, xAdd, yAdd)){
                this.x += xAdd;
                this.y += yAdd;
            }
        }
    }

    protected void checkDirection(int x, int y) {
        int currentDir;
        if (x != 0) {
            if (x == 1) {
                currentDir = RIGHT;
            } else {
                currentDir = LEFT;
            }
        } else {
            if (y == 1) {
                currentDir = DOWN;
            } else {
                currentDir = UP;
            }
        }
        changeDirection(currentDir);

    }

    protected void changeDirection(int currentDirection) {
        if (this.direction != currentDirection) {
            changeImageDir(currentDirection);
        }
    }

    protected void changeImageDir(int currentDirection) {
        switch (currentDirection) {
            case UP:
                this.currentImage = this.upImage;
                this.direction = UP;
                break;
            case DOWN:
                this.currentImage = this.downImage;
                this.direction = DOWN;
                break;
            case LEFT:
                this.currentImage = this.leftImage;
                this.direction = LEFT;
                break;
            case RIGHT:
                this.currentImage = this.rightImage;
                this.direction = RIGHT;
                break;
        }
    }

    public Image getImage() {
        return this.currentImage;
    }

    @Override
    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public Bullet spawnBullet(){
        return new Bullet(this.x, this.y, this.direction, this, this.tankDamage);
    }

    public void decrementHealth(int damage){
        this.health -= damage;
        if (this.health <= 0){
            this.die();
        }
    }

    public void addScoreWallShoot(){

    }
    public void addScoreTankShoot(){

    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
}
