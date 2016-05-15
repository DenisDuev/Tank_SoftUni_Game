package objects.game_objects.tanks;

import Interfaces.Effectable;
import javafx.scene.image.Image;

import static constants.Constants.*;

public class PlayerTank extends Tank{
    private int score;
    private String name;

    public PlayerTank(String name, int health, int x, int y) {
        super(health, x, y);
        this.name = name;
        this.tankDamage = 50;
    }

    @Override
    protected void initImages() {
        Image up = new Image("resources/tanks/silver_tank/silver_tank_up.png");
        Image down = new Image("resources/tanks/silver_tank/silver_tank_down.png");
        Image left = new Image("resources/tanks/silver_tank/silver_tank_left.png");
        Image right = new Image("resources/tanks/silver_tank/silver_tank_right.png");
        this.setImages(up, down, left, right);
    }

    @Override
    public void addScoreWallShoot(){
        this.score += 10;
    }

    @Override
    public void addScoreTankShoot(){
        this.score += 100;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void goToNextLevel(int x, int y){
        this.health = TANK_START_HEALTH;
        this.x = x;
        this.y = y;
        this.direction = UP;
        changeImageDir(this.direction);
    }
}
