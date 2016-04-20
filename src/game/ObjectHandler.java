package game;

import constants.Constants;
import objects.game_objects.Tanks.Explosion;
import objects.game_objects.Tanks.Bullet;

import java.util.ArrayList;
import java.util.List;

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
            bullet.Move();
            if (collisionDetector.isBulletCollide(bullet)) {
                bullets.remove(index);
                explosions.add(new Explosion(bullet.getX() - Constants.MATRIX_CELL_SIZE / 2, bullet.getY() - Constants.MATRIX_CELL_SIZE / 2));
            }
        }
        return bullets;
    }
}
