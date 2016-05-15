package objects.game_objects.tanks;

import constants.Constants;
import javafx.scene.image.Image;

public class EnemyBossTank extends EnemyTank {
    public static int BOSS_HEALTH = 300;
    private boolean goToBird;

    public EnemyBossTank(int health, int x, int y) {
        super(health, x, y);
        this.tankDamage = 100;
    }

    @Override
    protected int getDir() {
        if (this.goToBird) {
            this.goToBird = false;
            return super.getDir();
        } else {
            this.goToBird = true;
            return findBirdDir();
        }

    }

    private int findBirdDir() {
        if (this.getX() < Constants.BIRD_X && this.direction != LEFT) {
            return RIGHT;
        } else if (this.getY() < Constants.BIRD_Y) {
            return DOWN;
        } else if (this.getX() > Constants.BIRD_X + Constants.MATRIX_CELL_SIZE && this.direction != RIGHT) {
            return LEFT;
        } else {
            return this.direction;
        }
    }

    @Override
    protected void initImages() {
        Image up = new Image("resources/tanks/red_tank/red_tank_up.png");
        Image down = new Image("resources/tanks/red_tank/red_tank_down.png");
        Image left = new Image("resources/tanks/red_tank/red_tank_left.png");
        Image right = new Image("resources/tanks/red_tank/red_tank_right.png");
        this.setImages(up, down, left, right);
    }
}
