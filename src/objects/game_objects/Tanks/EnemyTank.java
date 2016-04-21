package objects.game_objects.Tanks;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * Created by Denis on 19.4.2016 �..
 */
public class EnemyTank extends Tank {
    private long lastTimeSpawn;

    public EnemyTank(String name, int health, int x, int y) {
        super(name, health, x, y);
        this.direction = getRandomDir();
        this.lastTimeSpawn = System.nanoTime();
    }

    @Override
    protected void initImages() {
        this.upImage = new Image("resources/tanks/green_tank/green_tank_up.png");
        this.downImage = new Image("resources/tanks/green_tank/green_tank_down.png");
        this.leftImage = new Image("resources/tanks/green_tank/green_tank_left.png");
        this.rightImage = new Image("resources/tanks/green_tank/green_tank_right.png");
        this.currentImage = new Image("resources/tanks/green_tank/green_tank_down.png");
    }

    public void moveEnemy() {
        int xChange = 0;
        int yChange = 0;
        switch (this.direction) {
            case UP:
                yChange--;
                break;
            case DOWN:
                yChange++;
                break;
            case LEFT:
                xChange--;
                break;
            case RIGHT:
                xChange++;
                break;
        }

        changeImageDir(this.direction);
        if (collisionDetector.shouldMove(this, xChange, yChange)) {
            this.x += xChange;
            this.y += yChange;
        } else {
            Random random = new Random();
            this.direction = random.nextInt(4);
        }
    }

    public boolean canShootBullet(long now) {
        if ((now - this.lastTimeSpawn) / 1_000_000_00.0 > 40){
            this.lastTimeSpawn = now;
            return true;
        }

        return false;
    }

    private int getRandomDir(){
        Random random = new Random();
        return random.nextInt(4);
    }
}

