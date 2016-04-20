package objects.game_objects.Tanks;

import objects.game_objects.Tanks.Tank;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class Player {
    private int lives;
    private Tank tank;
    private boolean isAlive;
    private int score;

    public Player(Tank tank) {
        this.tank = tank;
        this.lives = 3;
        this.isAlive = true;
    }

    public int getLives() {
        return lives;
    }

    public Tank getTank() {
        return tank;
    }

    public void removeLives(int lives) {
        this.lives--;
        if (this.lives <= 0){
            this.isAlive = false;
        }
    }

    public int getScore() {
        return score;
    }

    public void addScore(int addScore){
        if (addScore <= 0){
            throw new IllegalArgumentException("Score must be positive number");
        }

        this.score += addScore;
    }
}
