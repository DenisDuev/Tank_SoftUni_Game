package objects;

import javafx.scene.image.Image;

/**
 * Created by Denis on 15.4.2016 ã..
 */
public class Bullet extends GameObject{
    private int velocity;
    private Image Image;

    public Bullet(int x, int y) {
        super(x, y);
        this.velocity = 5;
        SetImage();
    }

    private void SetImage() {
        switch (this.direction){
            case UP:
                this.Image = new Image("resources/bullets/bullet_up.png");
                break;
            case DOWN:
                this.Image = new Image("resources/bullets/bullet_down.png");
                break;
            case LEFT:
                this.Image = new Image("resources/bullets/bullet_left.png");
                break;
            case RIGHT:
                this.Image = new Image("resources/bullets/bullet_right.png");
                break;
        }
    }
}
