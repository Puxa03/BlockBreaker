package LearnGround;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.scene.shape.Rectangle;



public class Sprite
{
    public Image image;
    public double positionX;
    public double positionY;

    public double width;
    public double height;

    // ...
    // methods omitted for brevity
    // ...


    public Sprite() {
    }

    public Sprite(Image image) {
        this.image = image;
        width=image.getWidth();
        height=image.getHeight();
    }

    public void render(GraphicsContext gc)
    {

        gc.drawImage( image, positionX, positionY );
    }


    public Rectangle getBoundary()
    {
        return new Rectangle(positionX,positionY,width,height);

    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(positionX,positionY,width,height);
    }
}
