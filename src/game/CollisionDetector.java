package game;

import objects.game_objects.Bullet;
import objects.game_objects.GameObject;
import objects.game_objects.power_ups.PowerUp;
import objects.game_objects.tanks.Tank;

import java.util.List;

import static constants.Constants.*;

public class CollisionDetector {
    private int[][] matrix;
    private List<Tank> tanks;
    private List<PowerUp> powerups;
    private boolean isGameOver;

    public CollisionDetector(int[][] matrix, List<Tank> tanks, List<PowerUp> powerups) {
        this.matrix = matrix;
        this.tanks = tanks;
        this.powerups = powerups;
        this.isGameOver = false;
    }

    public boolean shouldTankMove(Tank tank, int x, int y) {
        int tankX = tank.getX() + x;
        int tankY = tank.getY() + y;
        if (tankX < 0 || tankX + TANK_SIZE + 2 > BOARD_SIZE || tankY < 0 || tankY + TANK_SIZE + 2 > BOARD_SIZE) {
            return false;
        }

        if (matrix[tankY / MATRIX_CELL_SIZE][tankX / MATRIX_CELL_SIZE] <= 11 && matrix[tankY / MATRIX_CELL_SIZE][tankX / MATRIX_CELL_SIZE] > 0
                || matrix[tankY / MATRIX_CELL_SIZE][(tankX + TANK_SIZE) / MATRIX_CELL_SIZE] <= 11 && matrix[tankY / MATRIX_CELL_SIZE][(tankX + TANK_SIZE) / MATRIX_CELL_SIZE] > 0
                || matrix[(tankY + TANK_SIZE) / MATRIX_CELL_SIZE][(tankX + TANK_SIZE) / MATRIX_CELL_SIZE] <= 11 && matrix[(tankY + TANK_SIZE) / MATRIX_CELL_SIZE][(tankX + TANK_SIZE) / MATRIX_CELL_SIZE] > 0
                || matrix[(tankY + TANK_SIZE) / MATRIX_CELL_SIZE][tankX / MATRIX_CELL_SIZE] <= 11 && matrix[(tankY + TANK_SIZE) / MATRIX_CELL_SIZE][tankX / MATRIX_CELL_SIZE] > 0
                ) {
            return false;
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank otherTank = tanks.get(i);
            if (!otherTank.isAlive()) {
                tanks.remove(i);
            }
            //check the tanks by reference, if they are equal, we don't have to check for collision
            if (tank == otherTank) {
                continue;
            }

            //are 4 point of Tank inside the other tank
            boolean isUpLeftPoint = otherTank.getX() <= tankX && tankX <= otherTank.getX() + TANK_SIZE && otherTank.getY() <= tankY && tankY <= otherTank.getY() + TANK_SIZE;
            boolean isUpRightPoint = otherTank.getX() <= tankX + TANK_SIZE && tankX + TANK_SIZE <= otherTank.getX() + TANK_SIZE && otherTank.getY() <= tankY && tankY <= otherTank.getY() + TANK_SIZE;
            boolean isDownLeftPoint = otherTank.getX() <= tankX && tankX <= otherTank.getX() + TANK_SIZE && otherTank.getY() <= tankY + TANK_SIZE && tankY + TANK_SIZE <= otherTank.getY() + TANK_SIZE;
            boolean isDownRightPoint = otherTank.getX() <= tankX + TANK_SIZE && tankX + TANK_SIZE <= otherTank.getX() + TANK_SIZE && otherTank.getY() <= tankY + TANK_SIZE && tankY + TANK_SIZE <= otherTank.getY() + TANK_SIZE;

            if (isUpLeftPoint || isUpRightPoint || isDownLeftPoint || isDownRightPoint) {
                return false;
            }
        }

        return true;
    }

    public boolean isBulletCollide(Bullet bullet) {
        int bulletX = bullet.getX();
        int bulletY = bullet.getY();
        if (bulletX < 0 || bulletX > BOARD_SIZE - Bullet.IMAGE_WIGHT || bulletY < 0 || bulletY >= BOARD_SIZE) {
            return true;
        }

        if (matrix[bulletY / MATRIX_CELL_SIZE][bulletX / MATRIX_CELL_SIZE] <= 10 && matrix[bulletY / MATRIX_CELL_SIZE][bulletX / MATRIX_CELL_SIZE] > 0) {
            matrix[bulletY / MATRIX_CELL_SIZE][bulletX / MATRIX_CELL_SIZE]-= bullet.getDamage() / 10;
            bullet.addScoreToParentTankWallShoot();
            return true;
        }

        for (Tank tank : tanks) {
            if (tank.getX() <= bulletX && bulletX <= tank.getX() + TANK_SIZE && tank.getY() <= bulletY && bulletY <= tank.getY() + TANK_SIZE) {
                tank.decrementHealth(bullet.getDamage());
                bullet.addScoreToParentTankTankShoot();
                return true;
            }
        }
        //detectBird
        if (BIRD_X <= bulletX && bulletX <= BIRD_X + MATRIX_CELL_SIZE && BIRD_Y <= bulletY) {
            this.isGameOver = true;
            return false;
        }

        return false;
    }

    public boolean isBirdDeath(){
        return this.isGameOver;
    }

    public void getPowerUps(){
        for (int i = 0; i < this.powerups.size(); i++) {
            PowerUp powerup = this.powerups.get(i);
            if (!powerup.isAppear()){
                this.powerups.remove(i);
                continue;
            }

            for (Tank tank : this.tanks) {
                if (isObjectsCollide(powerup, tank, PowerUp.WIDTH )){
                    powerup.SetEffect(tank);
                    this.powerups.remove(i);
                    break;
                }
            }
        }
    }

    private static boolean isObjectsCollide(GameObject firstObeject, GameObject secondObject, int firstObjectSize) {
        boolean isUpLeftPoint    = secondObject.getX() <= firstObeject.getX() && firstObeject.getX() <= secondObject.getX() + firstObjectSize && secondObject.getY() <= firstObeject.getY() && firstObeject.getY() <= secondObject.getY() + firstObjectSize;
        boolean isUpRightPoint   = secondObject.getX() <= firstObeject.getX() + firstObjectSize && firstObeject.getX() + firstObjectSize <= secondObject.getX() + firstObjectSize && secondObject.getY() <= firstObeject.getY() && firstObeject.getY() <= secondObject.getY() + firstObjectSize;
        boolean isDownLeftPoint  = secondObject.getX() <= firstObeject.getX() && firstObeject.getX() <= secondObject.getX() + firstObjectSize && secondObject.getY() <= firstObeject.getY() + firstObjectSize && firstObeject.getY() + firstObjectSize <= secondObject.getY() + firstObjectSize;
        boolean isDownRightPoint = secondObject.getX() <= firstObeject.getX() + firstObjectSize && firstObeject.getX() + firstObjectSize <= secondObject.getX() + firstObjectSize && secondObject.getY() <= firstObeject.getY() + firstObjectSize && firstObeject.getY() + firstObjectSize <= secondObject.getY() + TANK_SIZE;

        return  (isUpLeftPoint || isUpRightPoint || isDownLeftPoint || isDownRightPoint);
    }
}


