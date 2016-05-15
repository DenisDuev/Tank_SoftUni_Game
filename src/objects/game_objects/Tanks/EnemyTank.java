package objects.game_objects.tanks;

import javafx.scene.image.Image;

import java.util.Random;

public class EnemyTank extends Tank {

    private long lastTimeSpawn;
    private boolean bulletShoot;
    private Random random;

    public EnemyTank(int health, int x, int y) {
        super(health, x, y);
        this.random = new Random();
        this.direction = getDir();
        this.lastTimeSpawn = System.nanoTime();
        this.tankDamage = 30;
    }

    @Override
    protected void initImages() {
        Image up = new Image("resources/tanks/green_tank/green_tank_up.png");
        Image left = new Image("resources/tanks/green_tank/green_tank_left.png");
        Image right = new Image("resources/tanks/green_tank/green_tank_right.png");
        Image down = new Image("resources/tanks/green_tank/green_tank_down.png");
        this.setImages(up, down, left, right);
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
        if (collisionDetector.shouldTankMove(this, xChange, yChange)) {
            this.x += xChange;
            this.y += yChange;
        } else {
            this.direction = getDir();
        }
    }

    public boolean canShootBullet(long now) {
        if ((((now - this.lastTimeSpawn) + generateRandomSecondDiff()) / 1_000_000_00.0) > 40) {
            this.lastTimeSpawn = now;
            bulletShoot = true;
            return true;
        }
        if (bulletShoot) {
            this.direction = getDir();
            bulletShoot = false;
        }
        return false;
    }

    protected int getDir() {
        return random.nextInt(4);
    }

    private int generateRandomSecondDiff(){
        return random.nextInt(80000);
    }
}

