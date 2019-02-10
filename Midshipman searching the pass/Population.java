import java.util.ArrayList;
import java.util.Random;
import java.lang.Thread;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Population {
    int populationSize = 200;
    int rememberI = 0;
    
    ArrayList<Object> rockets;
    ArrayList<Object> potencialParents;
    Object rt;
    
    public Random randomGenerator = new Random();
    
    public Population(){
        rockets = new ArrayList<Object>();
        for(int i = 0; i < populationSize; i++){
            rt = new Object();
            rockets.add(rt);
        }
    }
    
    //zapisywanie ostatniej ścieżki do pliku, dzięki czemu można ja bez problemu odtworzyc np na wykresie Excela
    public void save(Color color){
        try {
            File file = new File("test1.txt");
            FileWriter fileWriter = new FileWriter(file);
            
            for(int i=0; i<rockets.get(rememberI).pathX.size(); i++){
            fileWriter.write(Double.toString(rockets.get(rememberI).pathX.get(i)));
                fileWriter.write("\t");
            fileWriter.write(Double.toString(rockets.get(rememberI).pathY.get(i)));
                fileWriter.write("\n");
            }
            
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void evaluate(){
        
        double maxFit = 0;
        Object temp = new Object();
        
        //ocenia przystosowanie każdej kulki, implementacja w klasie Object
        for(int i = 0; i < populationSize; i++) {
            rockets.get(i).calcFitness();
            if (rockets.get(i).fitness > maxFit) {
                temp = rockets.get(i);
                maxFit = rockets.get(i).fitness;
                rememberI = i;
            }
        }
        
        //na wyjściu klasa wypisuje liczbę porządkową generacji oraz przystosowanie najlepszego rozwiązania - tutaj jest pewien problem, ponieważ przystosowanie powinno być liczone na podstawie drogi do mety w zakresie <0, 1>, jednak po pewnym czasie zauważyłem, że wyniki nie mają wiele wspólnego z rzeczywistością i nie umiem tego naprawić - widocznie gdzieś wkradł sie błąd na poziomie działań matematycznych i ciężko mi go odnaleźć, w związku z czym pominąłem wypisywanie zmiennej maxFit. Niemniej jednak wszystko działa.
        
        System.out.println("Generation: " + Main.ii + ", osobnik nr " + rememberI);
        save(Color.GREEN);
        
        for(int i = 0; i < populationSize; i++)
            rockets.get(i).fitness /= maxFit;

        //na podstawie wielkości przystosowania pętla tworzy konkretną liczbę osobnikow, które mogą stać się rodzicami następnych
        potencialParents = new ArrayList<Object>();
        
        for(int i = 0; i < populationSize; i++){
            //im większe przystosowanie, tym więcej rakiet (przystosowanie * 100 w celu wygenerowania z tych ułamków w miarę różnorodnych, całkowitych liczb)
            double n = rockets.get(i).fitness * 100;
            
            for(int j = 0; j < n; j++)
                potencialParents.add(rockets.get(i));
        }
        
        for(int i = 0; i < populationSize; i++) {
            rockets.get(i).pathX.clear();
            rockets.get(i).pathY.clear();
            
        }
    }

    public void selection(){
        ArrayList<Object> newRockets = new ArrayList<Object>();
        for(int i = 0; i < populationSize; i++){
            
            //losowanie rodziców A i B z potencialParents
            int index = randomGenerator.nextInt(potencialParents.size());
            DNA parentA = potencialParents.get(index).dna;
            
            index = randomGenerator.nextInt(potencialParents.size());
            DNA parentB = potencialParents.get(index).dna;
            
            DNA child;
            child = parentA.crossOver(parentB);
            child.mutation();
            
            newRockets.add(new Object(child));

        }
        rockets = newRockets;
    }

    public void run(){
        for(int i = 0; i < populationSize; i++){
            rockets.get(i).pathX.add(rockets.get(i).pos.x);
            rockets.get(i).pathY.add(rockets.get(i).pos.y);
            rockets.get(i).update();
            rockets.get(i).show(Main.gc);
        }
    }


}
