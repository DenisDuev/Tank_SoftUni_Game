package objects.game_objects.power_ups;

import Interfaces.Effectable;
import constants.Constants;
import javafx.scene.image.Image;

import java.util.Random;

public class LivePowerUp extends PowerUp  {

    public LivePowerUp(int x, int y) {
        super(x, y);
        this.image = new Image("resources/power_ups/heart.png");
    }

    @Override
    public void SetEffect(Effectable object) {
        object.EffectHealth(150);
    }

    public static PowerUp generateAtRandomLocation(){
        Random random = new Random();
        int x = random.nextInt(Constants.WINDOWS_HEIGHT - WIDTH);
        int y = random.nextInt(Constants.WINDOWS_HEIGHT - HEIGHT);
        return new LivePowerUp(x, y);
    }
}
