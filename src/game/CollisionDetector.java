package game;

import constants.Constants;
import objects.Bullet;
import objects.Tanks.Tank;

import java.util.List;

/**
 * Created by Denis on 14.4.2016 �..
 */
public class CollisionDetector {
    private int[][] matrix;
    private List<Tank> tanks;
    private boolean isGameOver;

    public CollisionDetector(int[][] matrix, List<Tank> tanks) {
        this.matrix = matrix;
        this.tanks = tanks;
        this.isGameOver = false;
    }

    public boolean shouldMove(Tank tank, int x, int y) {
        int tankX = tank.getX() + x;
        int tankY = tank.getY() + y;
        if (tankX < 0 || tankX + Constants.TANK_SIZE + 2 > Constants.BOARD_SIZE || tankY < 0 || tankY + Constants.TANK_SIZE + 2 > Constants.BOARD_SIZE) {
            return false;
        }

        if (matrix[tankY / Constants.MATRIX_CELL_SIZE][tankX / Constants.MATRIX_CELL_SIZE] <= 11 && matrix[tankY / Constants.MATRIX_CELL_SIZE][tankX / Constants.MATRIX_CELL_SIZE] > 0
                || matrix[tankY / Constants.MATRIX_CELL_SIZE][(tankX + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE] <= 11 && matrix[tankY / Constants.MATRIX_CELL_SIZE][(tankX + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE] > 0
                || matrix[(tankY + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE][(tankX + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE] <= 11 && matrix[(tankY + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE][(tankX + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE] > 0
                || matrix[(tankY + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE][tankX / Constants.MATRIX_CELL_SIZE] <= 11 && matrix[(tankY + Constants.TANK_SIZE) / Constants.MATRIX_CELL_SIZE][tankX / Constants.MATRIX_CELL_SIZE] > 0
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
            boolean isUpLeftPoint = otherTank.getX() <= tankX && tankX <= otherTank.getX() + Constants.TANK_SIZE && otherTank.getY() <= tankY && tankY <= otherTank.getY() + Constants.TANK_SIZE;
            boolean isUpRightPoint = otherTank.getX() <= tankX + Constants.TANK_SIZE && tankX + Constants.TANK_SIZE <= otherTank.getX() + Constants.TANK_SIZE && otherTank.getY() <= tankY && tankY <= otherTank.getY() + Constants.TANK_SIZE;
            boolean isDownLeftPoint = otherTank.getX() <= tankX && tankX <= otherTank.getX() + Constants.TANK_SIZE && otherTank.getY() <= tankY + Constants.TANK_SIZE && tankY + Constants.TANK_SIZE <= otherTank.getY() + Constants.TANK_SIZE;
            boolean isDownRightPoint = otherTank.getX() <= tankX + Constants.TANK_SIZE && tankX + Constants.TANK_SIZE <= otherTank.getX() + Constants.TANK_SIZE && otherTank.getY() <= tankY + Constants.TANK_SIZE && tankY + Constants.TANK_SIZE <= otherTank.getY() + Constants.TANK_SIZE;

            if (isUpLeftPoint || isUpRightPoint || isDownLeftPoint || isDownRightPoint) {
                return false;
            }
        }

        return true;
    }

    public boolean isBulletCollide(Bullet bullet) {
        int bulletX = bullet.getX();
        int bulletY = bullet.getY();
        if (bulletX < 0 || bulletX > Constants.BOARD_SIZE - Bullet.IMAGE_WIDHT || bulletY < 0 || bulletY >= Constants.BOARD_SIZE) {
            return true;
        }

        if (matrix[bulletY / Constants.MATRIX_CELL_SIZE][bulletX / Constants.MATRIX_CELL_SIZE] <= 10 && matrix[bulletY / Constants.MATRIX_CELL_SIZE][bulletX / Constants.MATRIX_CELL_SIZE] > 0) {
            matrix[bulletY / Constants.MATRIX_CELL_SIZE][bulletX / Constants.MATRIX_CELL_SIZE]--;
            bullet.getParentTank().addScoreWallShoot();
            return true;
        }

        for (Tank tank : tanks) {
            if (tank.getX() <= bulletX && bulletX <= tank.getX() + Constants.TANK_SIZE && tank.getY() <= bulletY && bulletY <= tank.getY() + Constants.TANK_SIZE) {
                tank.decrementHealth();
                bullet.getParentTank().addScoreTankShoot();
                return true;
            }
        }
        //detectBird
        if (8 * Constants.MATRIX_CELL_SIZE <= bulletX && bulletX <= 9 * Constants.MATRIX_CELL_SIZE && 18 * Constants.MATRIX_CELL_SIZE <= bulletY) {
            isGameOver = true;
            return false;
        }

        return false;
    }

    public boolean isBirdDeath(){
        return this.isGameOver;
    }
}


