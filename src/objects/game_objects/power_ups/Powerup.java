package objects.game_objects.power_ups;

import Interfaces.Drawable;
import Interfaces.Effectable;
import javafx.scene.canvas.GraphicsContext;
import objects.game_objects.GameObject;

public abstract class PowerUp extends GameObject implements Drawable {
    public static int WIDTH = 26;
    public static int HEIGHT = 26;

    private long iniTime;
    private long currentTime;
    private boolean isAppear;

    public boolean isAppear() {
        return this.isAppear;
    }

    public PowerUp(int x, int y) {
        super(x, y);
        this.iniTime = System.nanoTime();
        this.currentTime = this.iniTime;
        this.isAppear = true;
    }

    public abstract void SetEffect(Effectable object);

    @Override
    public void draw(GraphicsContext graphicsContext) {
        if ((this.currentTime - this.iniTime) /1000_000_000 < 10){
            graphicsContext.drawImage(this.getImage(), this.getX(), this.getY());
        } else {
            this.isAppear = false;
        }

        this.currentTime = System.nanoTime();
    }
}
