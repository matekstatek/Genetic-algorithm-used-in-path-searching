import javafx.scene.canvas.GraphicsContext;
import java.util.Scanner;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Path {
    public void show(GraphicsContext gc) throws Exception{
        ArrayList<Double> CoorX = new ArrayList<Double>();
        ArrayList<Double> CoorY = new ArrayList<Double>();
        
        Scanner sc = new Scanner(new BufferedReader(new FileReader("test1.txt")));
        
        while(sc.hasNextLine()) {
            String[] line = sc.nextLine().trim().split("\t");
            for (int j=0; j<line.length; j++) {
                if (j == 0) CoorX.add(Double.parseDouble(line[j]));
                else CoorY.add(Double.parseDouble(line[j]));
            }
        }
        
        //zaznacza trasę na czerwono, gdy nie trafił w cel lub na zielono wpp.
        if (CoorX.get(CoorX.size()-1) >= 200 && CoorX.get(CoorX.size()-1) <= 290 &&
            CoorY.get(CoorY.size()-1) >= 40 && CoorY.get(CoorY.size()-1) <= 170)
            gc.setFill(Color.GREEN);
        else gc.setFill(Color.RED);
        
        for(int i=0; i<CoorY.size(); i++){
            gc.fillRect(CoorX.get(i), CoorY.get(i), 7,7);
            
        }
    }
}
