import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Obstacle {
    public static int x, y, w, h;
    Image image = new Image("ceg.jpg");
    ImagePattern radialGradient = new ImagePattern(image, 20,20,600,600,false);

    public Obstacle(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    //pokazanie na animacji ścian
    public void show(GraphicsContext gc){
        gc.setFill(radialGradient);
        gc.fillRect(x, y, w, h);
        gc.fillRect(x-100, y+100, w-200, h+50);
        gc.fillRect(0,0,100,180);
        gc.fillRect(350,20,50,200);
        gc.fillRect(100,140,100,20);
        gc.fillRect(0,0,500,4);
        gc.fillRect(0,496,500,4);
        gc.fillRect(0,0,4,500);
        gc.fillRect(496,0,4,500);
    }
}
