package objects.Tanks;

import constants.Constants;
import game.CollisionDetector;
import javafx.scene.image.Image;
import objects.Bullet;
import objects.GameObject;

/**
 * Created by Denis on 13.4.2016 �..
 */
public class Tank extends GameObject {



    private String name;
    private int health;
    private int score;
    private Image upImage;
    private Image downImage;
    private Image leftImage;
    private Image rightImage;
    private Image currentImage;

    private CollisionDetector collisionDetector;


    public Tank(String name, int health, int x, int y)  {
        super(x,y);
        this.name = name;
        this.health = health;
        this.score = 0;
        this.upImage = new Image("resources/tanks/silver_tank/silver_tank_up.png");
        this.downImage = new Image("resources/tanks/silver_tank/silver_tank_down.png");
        this.leftImage = new Image("resources/tanks/silver_tank/silver_tank_left.png");
        this.rightImage = new Image("resources/tanks/silver_tank/silver_tank_right.png");
        this.currentImage = new Image("resources/tanks/silver_tank/silver_tank_up.png");
    }

    public void move(int xAdd, int yAdd) {
        checkDirection(xAdd, yAdd);

        if (collisionDetector.shouldMove(this, xAdd, yAdd)){
            this.x += xAdd;
            this.y += yAdd;
        }
    }

    private void checkDirection(int x, int y) {
        int currentDir = 0;
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
        ChangeDirection(currentDir);

    }

    private void ChangeDirection(int currentDirection) {
        if (this.direction != currentDirection) {
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
    }

    public Image getImage() {
        return this.currentImage;
    }


    public String getName() {
        return name;
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public Bullet spawnBullet(){
        return new Bullet(this.x, this.y, this.direction, this);
    }

    public void AddScoreWallShoot(){
        this.score += 100;
    }

    public int getScore() {
        return score;
    }
}
