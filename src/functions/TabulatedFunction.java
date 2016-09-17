package functions;

public class TabulatedFunction {
  static final double EPS = 1e-6;
  private double leftX, rightX;
  int pointsCount;
  private FunctionPoint[] points;

  public TabulatedFunction(double leftX, double rightX, int pointsCount) {
    this.leftX = leftX;
    this.rightX = rightX;
    this.pointsCount = pointsCount;
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    this.points = new FunctionPoint[pointsCount * 4];
    for (int i = 0; i < pointsCount - 1; i++) {
      points[i] = new FunctionPoint(leftX);
      leftX += deltaX;
    }
    if (pointsCount > 0) {
      points[pointsCount - 1] = new FunctionPoint(rightX);
    }
  }

  public TabulatedFunction(double leftX, double rightX, double[] values) {
    this.leftX = leftX;
    this.rightX = rightX;
    this.pointsCount = values.length;
    double deltaX = (rightX - leftX) / (pointsCount - 1);
    this.points = new FunctionPoint[pointsCount * 4];
    for (int i = 0; i < pointsCount - 1; i++) {
      points[i] = new FunctionPoint(leftX, values[i]);
      leftX += deltaX;
    }
    if (pointsCount > 0) {
      points[pointsCount - 1] = new FunctionPoint(rightX, values[pointsCount - 1]);
    }
  }


  public double getLeftDomainBorder() {
    return this.leftX;
  }

  public double getRightDomainBorder() {
    return this.rightX;
  }


  public double getFunctionValue(double x) {
    if (x < leftX || x > rightX) {
      return Double.NaN;
    }
    return getY(x);
  }

  private double getY(double valueX) {
    if (Math.abs(valueX - points[0].getX()) < EPS) {
      return points[0].getY();
    }
    for (int i = 1; i < pointsCount; i++) {
      if (Math.abs(valueX - points[i].getX()) < EPS) {
        return points[i].getY();
      }
      if (points[i - 1].getX() < valueX && valueX < points[i].getX()) {
        double dy = points[i].getY() - points[i - 1].getY();
        double dx = points[i].getX() - points[i - 1].getX();
        double dydx = dy / dx;
        return points[i - 1].getY() + (valueX - points[i - 1].getX()) * dydx;
      }
    }
    return Double.NaN;
  }


  public int getPointsCount() {
    return pointsCount;
  }

  public FunctionPoint getPoint(int index) {
    return new FunctionPoint(points[index]);
  }


  public void setPoint(int index, FunctionPoint point) {
    if (index < 0 || index >= pointsCount) {
      return;
    }
    if (point.getX() < leftX || rightX < point.getX()) {
      return;
    }
    removePoint(index);
    addPoint(point);
  }

  public void removePoint(int index) {
    points[index] = null;
    shiftToLeft(index + 1);
    pointsCount--;
  }

  private void shiftToLeft(int index) {
    for (int i = index; i < pointsCount; i++) {
      points[i - 1] = points[i];
      points[i] = null;
    }
  }

  public void addPoint(FunctionPoint point) {
    if (pointsCount == points.length - 2) {
      resize();
    }
    if (Math.abs(point.getX() - points[0].getX()) < EPS) {
      shiftToRight(0);
      points[0] = point;
    } else {
      for (int i = 1; i < pointsCount; i++) {
        if (Math.abs(point.getX() - points[i].getX()) < EPS
            || points[i - 1].getX() < point.getX() && point.getX() < points[i].getX()) {
          shiftToRight(i);
          points[i] = point;
        }
      }
    }
    pointsCount++;
    leftX = Math.min(leftX, point.getX());
    rightX = Math.max(rightX, point.getX());
  }

  private void resize() {
    FunctionPoint[] newPoints = new FunctionPoint[points.length * 4];
    System.arraycopy(newPoints, 0, points, 0, pointsCount);
    points = newPoints;
  }

  private void shiftToRight(int index) {
    for (int i = pointsCount - 1; i >= index; i--) {
      points[i + 1] = points[i];
      points[i] = null;
    }
  }


  public void setPointX(int index, double x) {
    if (0 < index || index >= points.length) {
      return;
    }
    FunctionPoint point = new FunctionPoint(x, points[index].getY());
    setPoint(index, point);
  }

  public void setPointY(int index, double y) {
    if (index < 0 || index >= pointsCount) {
      return;
    }
    points[index].setY(y);
  }


  public double getPointX(int index) {
    if (index < 0 || index >= pointsCount) {
      return Double.NaN;
    }
    return points[index].getX();
  }

  public double getPointY(int index) {
    if (index < 0 || index >= pointsCount) {
      return Double.NaN;
    }
    return points[index].getY();
  }
}