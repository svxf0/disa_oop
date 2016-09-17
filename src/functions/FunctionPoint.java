package functions;

public class FunctionPoint {

    private double x, y;

    public FunctionPoint(double x1, double y1) {
      x = x1;
      y = y1;
    }

    public FunctionPoint(FunctionPoint point) {
      y = (point.getY());
      x = (point.getX());
    }

    public FunctionPoint(){
      x = 0;
      y = 0;
    }

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

    public String toString() {
      return "(" + this.getX() + "; " + this.getY() + ")";
    }
}
