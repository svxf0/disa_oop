package functions;

public class TabulatedFunction {

  private FunctionPoint[] points = new FunctionPoint[0];

  private int pointCount = 0;

  private void expandPoints() {
    FunctionPoint[] newPoints = new FunctionPoint[points.length * 2];
    for (int i = 0; i < points.length; i++) {
      newPoints[i] = points[i];
    }
    for (int i = points.length; i < newPoints.length; i++) {
      newPoints[i] = null;
    }
    points = newPoints;
  }

  public FunctionPoint[] getPoints() {
    return points;
  }

  public void printPoints() {
    for (int i = 0; i < pointCount; i++) {
      System.out.println(points[i].toString());
    }
    System.out.println("\n");
  }

  public TabulatedFunction(double leftX, double rightX, int pointsCount) {
    double step = (rightX - leftX) / (pointsCount - 1);
    points = new FunctionPoint[pointsCount];
    for (int i = 0; i < pointsCount; i++) {
      if (i == pointsCount - 1) {
        points[i] = new FunctionPoint(rightX, 0);
      } else {
        points[i] = new FunctionPoint(leftX + step * i, 0);
      }
    }
    pointCount = pointsCount;
  }

  public TabulatedFunction(double leftX, double rightX, double[] values) {
    int pointsCount = values.length;
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

  public double getPointX(int i) {
    return points[i].getX();
  }

  public double getPointY(int i) {
    return points[i].getY();
  }

  public double getLeftDomainBorder() {
    return points[0].getX();
  }

  public double getRightDomainBorder() {
    return points[points.length - 1].getX();
  }

  private double[] getArgList() {
    double[] argList = new double[getPointCount()];
    for (int i = 0; i < points.length; i++) {
      argList[i] = points[i].getX();
    }
    return argList;
  }

  private double getPointValue(double x1, double x2, double y1, double y2, double x) {
    if (x1 == x2) return x1;
    return (x - x1) * (y2 - y1) / (x2 - x1) + y1;
  }

  public double getFunctionValue(double x) {
    int x1 = 0;
    int x2 = 0;
    if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
      return Double.NaN;
    }
    double[] args = getArgList();
    for (int i = 0; i < args.length; i++) {
      if (x == args[i]) {
        x1 = i;
        x2 = i;
        break;
      }
      if (x < args[i]) {
        x1 = i;
        x2 = i - 1;
        break;
      }
    }
    return getPointValue(getPointX(x1), getPointX(x2), getPointY(x1), getPointY(x2), x);
  }

  public int getPointCount() {
    return pointCount;
  }

  public FunctionPoint getPoint(int i) {
    return points[i];
  }

  private boolean caaaanDo(int i, double x) {
    boolean ok = true;
    if (i - 1 >= 0 && x < points[i - 1].getX()) {
      ok = false;
    }
    if (i + 1 < getPointCount() && x > points[i + 1].getX()) {
      ok = false;
    }
    return ok;
  }

  public void setPoint(int i, FunctionPoint point){
    if (caaaanDo(i, point.getX())) {
      points[i] = point;
    }
  }

  public void setPointX(int i, double x) {
    if (caaaanDo(i, x)) {
      points[i].setX(x);
    }
  }

  public void setPointY(int i, double y) {
    points[i].setY(y);
  }

  public void deletePoint(int index) {
    for (int i = index; i < getPointCount() - 1; i++) {
      points[i] = points[i + 1];
    }
    pointCount -= 1;
  }

  public void addPoint(FunctionPoint point) {
    if (points.length < getPointCount() + 1) {
      expandPoints();
    }
    if (point.getX() > points[getPointCount() - 1].getX()) {
      points[getPointCount()] = point;
      pointCount += 1;
      return;
    }
    double[] args = getArgList();
    FunctionPoint[] newPoints = new FunctionPoint[points.length];
    int index = 0;
    double x = point.getX();
    for (int i = 0; i < args.length; i++) {
      if (x < args[i]) {
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
}


