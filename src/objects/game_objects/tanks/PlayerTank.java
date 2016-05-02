package objects.game_objects.tanks;

import static constants.Constants.*;

public class PlayerTank extends Tank {
    private int score;
    private String name;

    public PlayerTank(String name, int health, int x, int y) {
        super(health, x, y);
        this.name = name;
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
