package LearnGround;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Block {
    private Image image;

    double positionX;
    double positionY;

    public double width;
    public double height;
    public boolean hit;

    public Block(Image image) {
        this.image = image;
        width=image.getWidth();
        height=image.getHeight();
        hit=false;
    }

    public void render(GraphicsContext gc)
    {
        if(!hit)
            gc.drawImage( image, positionX, positionY );
    }


    public Rectangle getBoundary()
    {
        return new Rectangle(positionX,positionY,width,height);

    }

}
