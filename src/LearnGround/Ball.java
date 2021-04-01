package LearnGround;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class Ball extends Sprite{
    public int angle;
    public double speedMultiplier;
    public Ball(Image image) {
        super(image);

        speedMultiplier=4;
        newAngle();
    }
    public void move(){

        this.positionX=(positionX+Math.cos(Math.toRadians(angle))*speedMultiplier);
        this.positionY=(positionY+Math.sin(-Math.toRadians(angle))*speedMultiplier);
    }

    public void newAngle(){
        Random generator=new Random();
        angle= 40+generator.nextInt(100);
    }
    public Circle getBoundaryC()
    {

        return new Circle(width/2);
    }


    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(positionX,positionY,width,height);
    }

    public boolean intersects(Block s)
    {

        return s.getBoundary().intersects(positionX,positionY,width,height);
    }

}
