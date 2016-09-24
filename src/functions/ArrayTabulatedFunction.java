package functions;

public class ArrayTabulatedFunction implements TabulatedFunction {

  private FunctionPoint[] points;

  private int pointCount = 0;

  public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
    if (leftX >= rightX || pointsCount < 2) {
      throw new IllegalArgumentException();
    }
    points = new FunctionPoint[pointsCount];
    double step = (rightX - leftX) / (pointsCount - 1);
    for (int i = 0; i < pointsCount; i++) {
      if (i == pointsCount - 1) {
        points[i] = new FunctionPoint(rightX, 0);
      } else {
        points[i] = new FunctionPoint(leftX + step * i, 0);
      }
    }
    pointCount = pointsCount;
  }

  public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
    int pointsCount = values.length;
    if (leftX >= rightX || pointsCount < 2) {
      throw new IllegalArgumentException();
    }
    points = new FunctionPoint[pointsCount];
    double step = (rightX - leftX) / (pointsCount - 1);
    for (int i = 0; i < pointsCount; i++) {
      if (i == pointsCount - 1) {
        points[i] = new FunctionPoint(rightX, values[i]);
      } else {
        points[i] = new FunctionPoint(leftX + step * i, values[i]);
      }
    }
    pointCount = pointsCount;
  }

  public ArrayTabulatedFunction(FunctionPoint[] allpoints) {
    if (allpoints.length < 2) {
      throw new IllegalArgumentException();
    }
    points = allpoints;
  }

  public int getPointCount() {
    return pointCount;
  }

  public double getLeftDomainBorder() {
    return points[0].getX();
  }

  public double getRightDomainBorder() {
    if (points.length == 0) {
      return Double.NaN;
    }
    return points[pointCount - 1].getX();
  }

  public double getPointX(int index) {
    if (index < 0 || index > pointCount) {
      throw new FunctionPointIndexOutOfBoundsException() ;
    }
    return points[index].getX();
  }

  public double getPointY(int index) {
    return points[index].getY();
  }

  public FunctionPoint getPoint(int index) {
    if (index < 0 || index > pointCount) {
      throw new FunctionPointIndexOutOfBoundsException() ;
    }
    return points[index];
  }

  private void expandPoints() {
    FunctionPoint[] newPoints = new FunctionPoint[points.length * 2];
    System.arraycopy(points, 0, newPoints, 0, points.length);
    points = newPoints;
  }

  private double[] getArgList() {
    double[] argList = new double[pointCount];
    for (int i = 0; i < points.length; i++) {
      argList[i] = points[i].getX();
    }
    return argList;
  }

  private double getPointValue(double x1, double x2, double y1, double y2, double x) {
    if (x1 == x2) {
      return x1;
    }
    return (x - x1) * (y2 - y1) / (x2 - x1) + y1;
  }

  public double getFunctionValue(double x) {
    int i1 = 0;
    int i2 = 0;
    if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
      return Double.NaN;
    }
    double[] args = getArgList();
    for (int i = 0; i < args.length; i++) {
      if (x == args[i]) {
        return getPointValue(getPointX(i), getPointX(i), getPointY(i), getPointY(i), x);
      }
      if (x < args[i]) {
        i1 = i;
        i2 = i - 1;
        return getPointValue(getPointX(i), getPointX(i - 1), getPointY(i), getPointY(i - 1), x);
      }
    }
    return 0;
  }

  private boolean caaaanDo(int i, double x) {
    if (i >= 1 && x < points[i - 1].getX()) {
      return false;
    }
    if (i + 1 < getPointCount() && x > points[i + 1].getX()) {
      return false;
    }
    return true;
  }

  public void setPoint(int i, FunctionPoint point) throws InappropriateFunctionPointException{
    if (i < 0 || i >= pointCount) {
      throw new FunctionPointIndexOutOfBoundsException() ;
    }
    if (caaaanDo(i, point.getX())) {
      points[i] = point;
      return;
    }
    throw new InappropriateFunctionPointException();
  }

  public void setPointX(int i, double x) throws InappropriateFunctionPointException {
    setPoint(i, new FunctionPoint(x, points[i].getY()));
  }

  public void setPointY(int index, double y) {
    if (index < 0 || index > pointCount) {
      throw new FunctionPointIndexOutOfBoundsException() ;
    }
    points[index].setY(y);
  }

  public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
    if (points.length < getPointCount() + 1) {
      expandPoints();
    }
    if (point.getX() > getRightDomainBorder()) {
      points[getPointCount()] = point;
      pointCount += 1;
      return;
    }
    double[] xs = getArgList();
    FunctionPoint[] newPoints = new FunctionPoint[points.length];
    int index = 0;
    double x = point.getX();
    for (int i = 0; i < xs.length; i++) {
      if (x == xs[i]) {
        throw new InappropriateFunctionPointException();
      }
      if (x < xs[i]) {
        index = i;
        break;
      }
    }
    System.arraycopy(points, 0, newPoints, 0, index - 1);
    newPoints[index] = point;
    System.arraycopy(points, index, newPoints, index + 1, getPointCount() - index);
    points = newPoints;
    pointCount += 1;
  }

  public void deletePoint(int index) {
    if (index < 0 || index >= pointCount) {
      throw new FunctionPointIndexOutOfBoundsException() ;
    }
    if (pointCount < 3) {
      throw new IllegalStateException();
    }
    for (int i = index; i < pointCount - 1; i++) {
      points[i] = points[i + 1];
    }
    pointCount -= 1;
  }

  public void printPoints() {
    System.out.println("Size: " + pointCount + "\n");
    for (int i = 0; i < pointCount; i++) {
      System.out.println(i + ": " + points[i].toString());
    }
  }

  public String getStringFunction() {
    String string = "" + this.getPointCount();
    for (int i = 0; i < pointCount; i++) {
      string = string + " " + points[i].getX() + " " + points[i].getY();
    }
    return string;
  }

  @Override
  public String toString() {
    String string = "";
    for (int i = 0; i < getPointCount(); i++) {
      string += points[i].toString() + " ,";
    }
    return string.substring(0, string.length() - 2);
  }
}


