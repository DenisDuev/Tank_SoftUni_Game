package game;

import objects.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class BulletHandler {
    private List<Bullet> bullets;
    private CollisionDetector collisionDetector;

    public BulletHandler(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
        this.bullets = new ArrayList<Bullet>();
    }

    public  void AddBullet(Bullet bullet){
        this.bullets.add(bullet);
    }

    public List<Bullet> GetBulletsToDraw(){
        for (int index = 0; index < bullets.size(); index++) {
            Bullet bullet = bullets.get(index);
            bullet.Move();
            if(!collisionDetector.isBulletCollide(bullet)){
                bullets.remove(index);
            }
        }
        return bullets;
    }
}
