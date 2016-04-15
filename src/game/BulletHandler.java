package game;

import constants.Constants;
import objects.Boom;
import objects.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Denis on 15.4.2016 �..
 */
public class BulletHandler {
    private List<Bullet> bullets;
    private CollisionDetector collisionDetector;
    private  List<Boom> booms;

    public BulletHandler(CollisionDetector collisionDetector, List<Boom> booms) {
        this.collisionDetector = collisionDetector;
        this.bullets = new ArrayList<Bullet>();
        this.booms = booms;
    }

    public  void AddBullet(Bullet bullet){
        this.bullets.add(bullet);
    }

    public List<Bullet> GetBulletsToDraw(){
        for (int index = 0; index < bullets.size(); index++) {
            Bullet bullet = bullets.get(index);
            bullet.Move();
            if(collisionDetector.isBulletCollide(bullet)){
                bullets.remove(index);
                booms.add(new Boom(bullet.getX() - Constants.MATRIX_CELL_SIZE/2, bullet.getY() - Constants.MATRIX_CELL_SIZE /2));
            }
        }
        return bullets;
    }
}
