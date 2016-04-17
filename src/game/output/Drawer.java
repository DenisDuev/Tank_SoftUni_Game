package game.output;

import constants.Constants;
import game.ObjectHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.Bullet;
import objects.Explosion;
import objects.Tanks.Tank;

import java.util.List;

/**
 * Created by Denis on 17.4.2016 ã..
 */
public class Drawer {

    public static void DrawWalls(GraphicsContext gc, Image[] walls, int[][] matrix) {
        int wallIndex;
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                wallIndex = matrix[row][col];
                if (wallIndex == 12) {
                    //green
                    gc.drawImage(walls[3], col * Constants.MATRIX_CELL_SIZE, row * Constants.MATRIX_CELL_SIZE);
                } else if (wallIndex == 11){
                    //water
                    gc.drawImage(walls[2], col * Constants.MATRIX_CELL_SIZE, row * Constants.MATRIX_CELL_SIZE);
                } else if (wallIndex < 3 && wallIndex > 0){
                    //ordinary
                    gc.drawImage(walls[0], col * Constants.MATRIX_CELL_SIZE, row * Constants.MATRIX_CELL_SIZE);
                } else if (wallIndex <= 10 && wallIndex > 0){
                    //metal
                    gc.drawImage(walls[1], col * Constants.MATRIX_CELL_SIZE, row * Constants.MATRIX_CELL_SIZE);
                }
            }
        }
    }

    public static void drawCellsMapEdit(GraphicsContext graphicsContext, Image[] walls, int[][] matrix){
        for (int row = 0; row < Constants.MATRIX_ROWS; row++) {
            for (int col = 0; col < Constants.MATRIX_COLS; col++) {
                graphicsContext.drawImage(walls[matrix[row][col]], row * Constants.MATRIX_CELL_SIZE, col *  Constants.MATRIX_CELL_SIZE);
            }
        }
        graphicsContext.drawImage(new Image("resources/bird.png"),  9 * Constants.MATRIX_CELL_SIZE, 19 * Constants.MATRIX_CELL_SIZE);
    }

    public static void DrawExplosions(GraphicsContext gc, List<Explosion> explosions) {
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);
            gc.drawImage(explosion.getImage(), explosion.getX(), explosion.getY());
            if (explosion.decrementFrames()){
                explosions.remove(i);
            }
        }
    }

    public static void DrawBullets(GraphicsContext gc, ObjectHandler objectHandler) {
        List<Bullet> bullets = objectHandler.GetBulletsToDraw();
        for (Bullet bullet : bullets) {
            gc.drawImage(bullet.getImage(), bullet.getX(), bullet.getY());
        }
    }

    public static void  DrawTank(GraphicsContext graphicsContext, Tank tank){
        graphicsContext.drawImage(tank.getImage(), tank.getX(), tank.getY());
    }
}
