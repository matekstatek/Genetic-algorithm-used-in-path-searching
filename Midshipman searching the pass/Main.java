import javafx.animation.AnimationTimer;
import java.lang.Thread;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;

/* na początku pragnę zaznaczyć, że program działa dość... "deterministycznie". Mam tu na myśli, że jeśli generacja znajdzie pewną drogę, to istnieje bardzo małe prawdopodobieństwo, iż znajdzie inne (w przypadku tego konkretnego labiryntu nie zdarzyło się to ani razu, w łatwiejszych przypadkach również bardzo rzadko - około 5 razy od początku testowania programu. Prawdopodobnie jest to wina małego procentu szansy na mutację (mutacja jednego genu przy 1% też niewiele zmienia, bo to tak naprawdę średnio 4 odmienne ruchy na 400 możliwych, prędzej uderzy w ścianę niż znajdzie coś innego). Algorytm jednak rozwiązuje problem i napisany jest w miarę przejrzyście, więc mam nadzieję, że spełni Pańskie oczekiwania.
    Najniebezpieczniejsze są osobniki, które w danej generacji nie trafiły w cel ani nie uderzyły w ścianę - ich przystosowanie w stosunku do tych drugich jest 10-krotnie większe i bywa, iż niektóre jednostki mogą sobie błądzić bez celu - nie jest to jednak różnica na tyle duża, by nie móc odnaleźć prawidłowej ścieżki.
 */
public class Main extends Application {
    Image img = new Image("amw.jpg");
    public static int width = 500, height = 500;
    public static GraphicsContext gc;
    public Path path;
    public Population population;
    public Target target;
    public Obstacle obstacle;
    public Obstacle obstacle1;
    
    /* lifespan to czas działania jednej generacji - jest to jednostka czasu dla timera.
     Postanowilem, iż program będzie działał w ten sposób, że w każdej jednostce czasu będzie podejmował decyzję dotyczącą kierunku oraz prędkości ruchu jednostki. 400 jest wartością wybraną na zasadzie prób i błędów - po tym czasie zazwyczaj populacja już się nie rusza i nie ma sensu czekać na jednostki przy tak prostym projekcie (oczywiście im cięższy labirynt, tym więcej czasu należy przeznaczyć). Minusem tak krótkiego działania generacji jest odcinanie dróg, których znalezienie wymaga więcej czasu.
     Sama oprawa graficzna była wymysłem praktyk, by dodać duszy projektowi - ostatecznie wygląda ładniej, niż czarne kropki zmierzające w stronę czerwonej.
     */
    public static int lifespan = 400;
    public static int count = 0;
    public static int ii = 1;

    //nadpisana metoda start klasy AnimationTimer, tworzy populacje, ściany i cel
    @Override
    public void start(Stage appScreen) throws Exception{
        appScreen.setTitle("Algorytm genetyczny bsm. pchor. Mateusz SOBOLEWSKI");
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        Group group = new Group();
        group.getChildren().add(canvas);
        animationStart();

        population = new Population();
        target = new Target();
        obstacle = new Obstacle(100, 200, 300, 50);
        path = new Path();
       
        Scene scn = new Scene(group, width, height);
        appScreen.setScene(scn);
        appScreen.show();
    }

    public void animationStart(){
        AnimationTimer timer;

        timer = new AnimationTimer() {
            
            @Override //nadpisana metoda handle
            public void handle(long l) {
                gc.clearRect(0,0, width,height);
                gc.drawImage(img, 0, 0, width, height);
                
                try{
                    path.show(gc);
                }
                catch(Exception e) { }
                
                target.show(gc);
                obstacle.show(gc);
                population.run();
                
                count++;
                if(count >= lifespan){
                    population.evaluate();
                    population.selection();
                    count = 0;
                    ii ++;
                }
            }

        };
        
        timer.start();
    }

    /*
        dokumentacja JavaFX mówi, że poprawnie zbudowana aplikacja JavaFX
        ignoruje metodę main, jednak bez wywołania metody launch() klasy Application
        program nie wykonuje się poprawnie.
     */
    public static void main(String[] args) {
        launch();
    }
}
