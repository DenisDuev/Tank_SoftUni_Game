package game;

import objects.game_objects.Explosion;
import objects.game_objects.Bullet;

import java.util.ArrayList;
import java.util.List;

import static constants.Constants.*;

/**
 * Created by Denis on 15.4.2016 �..
 */
public class ObjectHandler {
    private List<Bullet> bullets;
    private CollisionDetector collisionDetector;
    private List<Explosion> explosions;

    public ObjectHandler(CollisionDetector collisionDetector, List<Explosion> explosions) {
        this.collisionDetector = collisionDetector;
        this.bullets = new ArrayList<>();
        this.explosions = explosions;
    }

    public void AddBullet(Bullet bullet) {
        this.bullets.add(bullet);
    }

    public List<Bullet> GetBulletsToDraw() {
        for (int index = 0; index < bullets.size(); index++) {
            Bullet bullet = bullets.get(index);
            bullet.move();
            if (collisionDetector.isBulletCollide(bullet)) {
                bullets.remove(index);
                explosions.add(new Explosion(bullet.getX() - MATRIX_CELL_SIZE / 2, bullet.getY() - MATRIX_CELL_SIZE / 2));
            }
        }
        return bullets;
    }
}
