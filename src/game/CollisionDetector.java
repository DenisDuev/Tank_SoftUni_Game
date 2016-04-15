package game;

import constants.Constants;
import objects.Tanks.Tank;

import java.util.List;

/**
 * Created by Denis on 14.4.2016 �..
 */
public class CollisionDetector {
    private int[][] matrix;
    private List<Tank> tanks;

    public CollisionDetector(int[][] matrix, List<Tank> tanks) {
        this.matrix = matrix;
        this.tanks = tanks;
    }

    public boolean shouldMove(Tank tank, int x, int y) {
        int tankX = tank.getX() + x;
        int tankY = tank.getY() + y;
        if (tankX < 0 || tankX + Constants.TANK_SIZE > Constants.BOARD_SIZE || tankY < 0 || tankY + Constants.TANK_SIZE > Constants.BOARD_SIZE) {
            return false;
        }

        if (matrix[tankY / 30][tankX / 30] != 0
                || matrix[tankY / 30][tankX / 30 + 1] != 0
                || matrix[tankY / 30 + 1][tankX / 30 + 1] != 0
                || matrix[tankY / 30 + 1][tankX / 30] != 0
                ) {
            return false;
        }

        for (Tank otherTank : tanks) {
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
}
