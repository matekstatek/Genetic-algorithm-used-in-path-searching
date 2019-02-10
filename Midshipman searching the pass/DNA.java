import java.util.ArrayList;
import java.util.Random;

public class DNA {
    ArrayList<Vector>genes;
    Random rand;

    public DNA(){
        genes = new ArrayList<Vector>();
        for(int i = 0; i < Main.lifespan+1; i++){
            //tyle, ile trwa animacja, tyle tworzy genów
            //jeden gen to randomowa wartość x i y
            // (-1, -0,5> i (0, 0,5)
            Vector vr = new Vector(0, 0);
            vr.randomV();
            genes.add(vr);
        }//dnp
    }

    public DNA(ArrayList<Vector> genes){
        this.genes = genes;
    }

    public DNA crossOver(DNA partner){
        //metoda typu DNA -> tworzy nowe geny na podstawie krzyżowania poprzednich
        ArrayList<Vector> newGenes = new ArrayList<Vector>();
        rand = new Random();
        
        int mid = rand.nextInt(genes.size());
        
        for(int i = 0; i < Main.lifespan; i++){
            if(i > mid)
                newGenes.add(genes.get(i));
            else
                newGenes.add(partner.genes.get(i));
        }
        return new DNA(newGenes);

    }
    public void mutation(){
        for (int i = 0; i < genes.size(); i++){
            //mutacja na poziomie 1% (wymierzone na zasadzie prób i błędów)
            if(Math.random() < 0.01){
                Vector vec = new Vector(0,0);
                vec.randomV();
                vec.limit(1);
                genes.set(i, vec);
            }
        }
    }

}
