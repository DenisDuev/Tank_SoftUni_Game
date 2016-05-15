package game;

import objects.game_objects.Explosion;
import objects.game_objects.Bullet;

import java.util.ArrayList;
import java.util.List;

public class ObjectHandler {
    private List<Bullet> bullets;
    private CollisionDetector collisionDetector;
    private List<Explosion> explosions;

    public ObjectHandler(CollisionDetector collisionDetector, List<Explosion> explosions) {
        this.collisionDetector = collisionDetector;
        this.bullets = new ArrayList<>();
        this.explosions = explosions;
    }

    public void addBullet(Bullet bullet) {
        this.bullets.add(bullet);
    }

    public List<Bullet> getBulletsToDraw() {
        for (int index = 0; index < this.bullets.size(); index++) {
            Bullet bullet = this.bullets.get(index);
            bullet.move();
            if (this.collisionDetector.isBulletCollide(bullet)) {
                this.bullets.remove(index);
                this.explosions.add(new Explosion(bullet));
            }
        }
        return bullets;
    }

    public void managePowerUps(){
        this.collisionDetector.getPowerUps();
    }
}
