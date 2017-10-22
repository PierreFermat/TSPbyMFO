import static java.lang.Math.sqrt;

public class Destination {
    public int x;
    public int y;
    public int location;

    public Destination(int x, int y, int location) {
        this.x = x;
        this.y = y;
        this.location = location;
    }
    public Destination subtract(Destination other){
        return new Destination(this.x - other.x, this.y - other.y,0);
    }
    public double magnitude() {
        return sqrt(this.x * this.x + this.y * this.y);
    }
    public double distanceWith(Destination other){
        return this.subtract(other).magnitude();
    }
}



