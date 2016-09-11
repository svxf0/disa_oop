package functions;

public class FunctionPoint implements Comparable<FunctionPoint>{

    private double x, y;

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public FunctionPoint(double x, double y) {
        setX(x);
        setY(y);
    }

    public FunctionPoint(FunctionPoint point) {
        setY(point.getY());
        setX(point.getX());
    }

    public FunctionPoint(){
        setX(0);
        setY(0);
    }

    public String toString() {
      return String.valueOf("(" + this.getX() + "; " + this.getY() + ")");
    }

    @Override
    public int compareTo(FunctionPoint o) {
        return Double.compare(this.x, o.x);
    }
}
