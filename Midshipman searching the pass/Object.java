import javafx.scene.canvas.GraphicsContext;
import static java.lang.Math.random;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class Object {
    ArrayList<Double> pathX;
    ArrayList<Double> pathY;
    Image img;
    Vector pos;
    Vector vel;
    Vector acc;
    DNA dna;
    int size = 5;
    double fitness = 0;
    boolean completed = false;
    boolean crashed = false;

    public Object(){ //pierwsze wywołanie konstruktora, brak genów
        img = new Image("arek.png");
        double randomX = random();
        if(random() > 0.5){
            randomX = -randomX;
        }
        double randomY = random();
        
        //początkowa lokalizacja
        pos = new Vector(Main.width/2 - 5, Main.height - 10);
        vel = new Vector(randomX, randomY);
        acc = new Vector(0, 0);
        dna = new DNA();
        pathX = new ArrayList<Double>();
        pathY = new ArrayList<Double>();
    }

    public Object(DNA dna){
        img = new Image("arek.png");
        double randomX = random();
        if(random() > 0.5){
            randomX = -randomX;
        }
        double randomY = random();
        pos = new Vector(Main.width/2 - 5, Main.height - 10);
        vel = new Vector(randomX, randomY);
        acc = new Vector(0, 0);
        this.dna = dna;
        pathX = new ArrayList<Double>();
        pathY = new ArrayList<Double>();
    }

    public void show(GraphicsContext gc){
        gc.drawImage(img, pos.x-10, pos.y-10);
    }

    public void addForce(Vector force){
        acc.add(force);
    }

    public void update(){ //obsługa zderzeń z przeszkodami lub dotarcia do celu
        if(Vector.dist(pos.x, Target.pos.x+40, pos.y, Target.pos.y+40)  < 40){
            completed = true;
        }
        
        if(pos.x > Obstacle.x && pos.x < Obstacle.x + Obstacle.w && pos.y > Obstacle.y && pos.y < Obstacle.y + Obstacle.h ){
            crashed = true;
        }
        if(pos.x > Obstacle.x-100 && pos.x < Obstacle.x-100 + Obstacle.w-200 && pos.y > Obstacle.y+100 && pos.y < Obstacle.y+100 + Obstacle.h+50 ){
            crashed = true;
        }
        
        if(pos.x > 0 && pos.x < 100 && pos.y > 0 && pos.y < 180 ){
            crashed = true;
        }

        if(pos.x > 350 && pos.x < 400 && pos.y > 20 && pos.y < 220 ){
            crashed = true;
        }
        
        if(pos.x > 100 && pos.x < 200 && pos.y > 140 && pos.y < 160 ){
            crashed = true;
        }

        if(pos.x < 4 || pos.x > Main.width-5 || pos.y > Main.height-5 || pos.y < 4){
            crashed = true;
        }
        
        
        if(!completed && !crashed) {
            this.addForce(this.dna.genes.get(Main.count));
            vel.add(acc);
            vel.minus();
            pos.add(vel);
            vel.minus();
            acc.mult(0);
            vel.limit(3);
        }
    }

    public void calcFitness(){
        //wzmacnianie lub osłabianie przystosowania (w zależności, czy jednostka dotarła, czy nie
        double d = Vector.dist(pos.x, Target.pos.x, pos.y, Target.pos.y);
        d = 1/d;
        if(completed)
            d *= 10;
            
        if(crashed)
            d/=10;

        fitness = d;
    }
}


