package functions;

import java.util.Objects;

public class FunctionPoint {

    private double x, y;

    public FunctionPoint(double x1, double y1) {
      x = x1;
      y = y1;
    }

    public FunctionPoint(FunctionPoint point) {
      y = point.y;
      x = point.x;
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
      return "(" + x + "; " + y + ")";
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    FunctionPoint that = (FunctionPoint) o;

    if (Double.compare(that.getX(), getX()) != 0) return false;
    return Double.compare(that.getY(), getY()) == 0;

  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(getX());
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(getY());
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
