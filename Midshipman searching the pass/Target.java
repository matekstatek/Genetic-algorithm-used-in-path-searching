import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

public class Target {
    public static Vector pos;
    public int size = 100;

    public Target(){
        pos = new Vector(230, 100);
    }

    public void show(GraphicsContext gc){
        gc.drawImage(new Image("pj.jpg"), pos.x, pos.y, size, size);
    }
}
