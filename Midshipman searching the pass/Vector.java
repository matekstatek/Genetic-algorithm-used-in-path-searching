public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector vec){
        x += vec.x;
        y += vec.y;
    }

    public void mult(double value){
        x *= value;
        y *= value;
    }

    public void limit(double value){
        if (x > value) x = value;
        if (y > value) y = value;
    }

    public void minus(){
        x = -x;
        y = -y;
    }

    public void randomV(){
        x = Math.random();
        y = Math.random();
        if (Math.random() >= 0.5 ) x = -x;
        if (Math.random() >= 0.5 ) y = -y;
    }

    public static double dist(double x1, double x2, double y1, double y2){
        return Math.sqrt((x2 - x1) * (x2 -x1) + (y2-y1) * (y2-y1));
    }
}
