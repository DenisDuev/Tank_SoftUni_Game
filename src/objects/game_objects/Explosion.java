package objects.game_objects;

import Interfaces.Drawable;
import constants.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion extends GameObject implements Drawable {
    private int frames;

    public Explosion(int x, int y) {
        super(x, y);
        this.image = new Image("resources/explosion.png");
        this.frames = 10;
    }

    public Explosion(Bullet parentBullet){
        super(parentBullet.getX() - Constants.MATRIX_CELL_SIZE / 2, parentBullet.getY() - Constants.MATRIX_CELL_SIZE / 2);
        this.image = new Image("resources/explosion.png");
        this.frames = 10;
    }

    public boolean isExplosionFinished(){
        this.decrementFrames();
        return this.frames <= 0;
    }

    private void decrementFrames() {
        this.frames--;
    }

    @Override
    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
    }
}
