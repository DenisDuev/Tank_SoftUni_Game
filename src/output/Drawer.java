package output;

import game.ObjectHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.game_objects.Bullet;
import objects.game_objects.Explosion;
import objects.game_objects.tanks.Tank;

import java.util.List;

import static constants.Constants.*;

public class Drawer {

    public static void drawWalls(GraphicsContext gc, Image[] walls, int[][] matrix) {
        int wallIndex;
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                wallIndex = matrix[row][col];
                if (wallIndex == 12) {
                    //green
                    gc.drawImage(walls[3], col * MATRIX_CELL_SIZE, row * MATRIX_CELL_SIZE);
                } else if (wallIndex == 11) {
                    //water
                    gc.drawImage(walls[2], col * MATRIX_CELL_SIZE, row * MATRIX_CELL_SIZE);
                } else if (wallIndex <= 3 && wallIndex > 0) {
                    //ordinary
                    gc.drawImage(walls[0], col * MATRIX_CELL_SIZE, row * MATRIX_CELL_SIZE);
                } else if (wallIndex <= 10 && wallIndex > 0) {
                    //metal
                    gc.drawImage(walls[1], col * MATRIX_CELL_SIZE, row * MATRIX_CELL_SIZE);
                }
            }
        }
    }

    public static void drawCellsMapEdit(GraphicsContext graphicsContext, Image[] walls, int[][] matrix) {
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLS; col++) {
                graphicsContext.drawImage(walls[matrix[row][col]], col * MATRIX_CELL_SIZE, row * MATRIX_CELL_SIZE);
            }
        }
        graphicsContext.drawImage(new Image("resources/bird.png"), 9 * MATRIX_CELL_SIZE, 19 * MATRIX_CELL_SIZE);
    }

    public static void drawExplosions(GraphicsContext gc, List<Explosion> explosions) {
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            explosion.draw(gc);
            if (explosion.isExplosionFinished()) {
                explosions.remove(i);
            }
        }
    }

    public static void drawBullets(GraphicsContext gc, ObjectHandler objectHandler) {
        List<Bullet> bullets = objectHandler.getBulletsToDraw();
        for (Bullet bullet : bullets) {
            bullet.draw(gc);
        }
    }

    public static void drawTank(GraphicsContext graphicsContext, Tank tank) {
        if (tank.isAlive()) {
            tank.draw(graphicsContext);
        }
    }

    public static void drawBird(GraphicsContext graphicsContext, Image bird) {
        graphicsContext.drawImage(bird, BIRD_X, BIRD_Y);
    }

    public static void drawBackground(GraphicsContext graphicsContext, Image background) {
        graphicsContext.drawImage(background, 0, 0);
    }
}
