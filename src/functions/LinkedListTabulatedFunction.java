package functions;

import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction implements TabulatedFunction {

  private class FunctionNode {

    private FunctionPoint point;
    private FunctionNode prev;
    private FunctionNode next;

    private FunctionNode(FunctionNode prev, FunctionPoint point, FunctionNode next) {
      this.point = point;
      this.next = next;
      this.prev = prev;
    }

    private FunctionNode addBefore(FunctionPoint point) {
      FunctionNode newNode = new FunctionNode(this.prev, point, this);
      newNode.prev.next = newNode;
      newNode.next.prev = newNode;
      size++;
      return newNode;
    }

    private FunctionNode removeNode() {
      if (this == header) {
        throw new NoSuchElementException();
      }
      this.prev.next = this.next;
      this.next = this.prev;
      this.point = null;
      size--;
      return this;
    }
  }

  private int size;
  private FunctionNode header = new FunctionNode(null, null, null);
  private FunctionNode currentNode;

  public LinkedListTabulatedFunction() {
    header.next = header.prev = header;
  }

  public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
    if (leftX >= rightX || pointsCount < 2) {
      throw new IllegalArgumentException();
    }
    header.next = header.prev = header;
    double step = (rightX - leftX) / (pointsCount - 1);
    for (int i = 0; i < pointsCount; i++) {
      if (i == pointsCount - 1) {
        addNodeToTail(new FunctionPoint(rightX, 0));
      } else {
        addNodeToTail(new FunctionPoint(leftX + step * i, 0));
      }
    }
  }

  public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
    int pointsCount = values.length;
    if (leftX >= rightX || pointsCount < 2) {
      throw new IllegalArgumentException();
    }
    header.next = header.prev = header;
    double step = (rightX - leftX) / (pointsCount - 1);
    for (int i = 0; i < pointsCount; i++) {
      if (i == pointsCount - 1) {
        addNodeToTail(new FunctionPoint(rightX, values[i]));
      } else {
        addNodeToTail(new FunctionPoint(leftX + step * i, values[i]));
      }
    }
  }

  public LinkedListTabulatedFunction(FunctionPoint[] points) {
    if (points.length < 2) {
      throw new IllegalArgumentException();
    }
    header.next = header.prev = header;
    for (int i = 0; i < points.length; i++) {
      addNodeToTail(points[i]);
    }
  }


  private FunctionNode addNodeToTail(FunctionPoint point) {
    return header.addBefore(point);
  }

  private FunctionNode getNodeByIndex(int index) {
    if (index >= size || index < 0) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    currentNode = header.next;
    for (int i = 0; i < index; i++) {
      currentNode = currentNode.next;
    }
    return currentNode;
  }

  private FunctionNode addNodeByIndex(int index, FunctionNode node) {
    if (index >= size || index < 0) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    currentNode = getNodeByIndex(index).addBefore(node.point);
    size++;
    return currentNode;
  }

  private FunctionNode deleteNodeByIndex(int index) {
    if (index < 0 || index > size) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    return getNodeByIndex(index).removeNode();
  }

  public int getPointCount() {
    return size;
  }

  public double getPointX(int index) {
    if (index < 0 || index > size) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    return getNodeByIndex(index).point.getX();
  }

  public double getPointY(int index) {
    return getNodeByIndex(index).point.getY();
  }

  public FunctionPoint getPoint(int index) {
    if (index < 0 || index > size) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    return getNodeByIndex(index).point;
  }

  public double getLeftDomainBorder() {
    return getPointX(0);
  }

  public double getRightDomainBorder() {
    if (size == 0) {
      return Double.NaN;
    }
    return getPointX(size - 1);
  }

  private double[] getArgList() {
    double[] argList = new double[size];
    for (int i = 0; i < size; i++) {
      argList[i] = getPointX(i);
    }
    return argList;
  }

  private double getPointValue(double x1, double x2, double y1, double y2, double x) {
    if (x1 == x2) {
      return x1;
    }
    System.out.println((x - x1) * (y2 - y1) / (x2 - x1) + y1);
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

  public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
    if (index < 0 || index >= size) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    System.out.println((point.getX()));
    System.out.println(getNodeByIndex(index - 1).point.getX());
    if ((index >= 1 && point.getX() < getNodeByIndex(index - 1).point.getX()) || (index < size - 1 && point.getX() > getNodeByIndex(index + 1).point.getX())) {
      throw new InappropriateFunctionPointException();
    }
    getNodeByIndex(index).point = point;
  }

  public void setPointX(int i, double x) throws InappropriateFunctionPointException {
    setPoint(i, new FunctionPoint(x, getPointY(i)));
  }

  public void setPointY(int index, double y) {
    if (index < 0 || index > size) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    getNodeByIndex(index).point.setY(y);
  }

  public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
    double[] xs = getArgList();
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
    if (index == 0) {
      header.addBefore(point);
      return;
    }
    getNodeByIndex(index).addBefore(point);
  }

  public void deletePoint(int index) {
    if (index < 0 || index >= size) {
      throw new FunctionPointIndexOutOfBoundsException();
    }
    deleteNodeByIndex(index);
  }

  public String getStringFunction() {
    currentNode = header;
    String string = "" + size;
    for (int i = 0; i < size; i++) {
      currentNode = currentNode.next;
      string += " " + currentNode.point.getX() + " " + currentNode.point.getY();
    }
    return string;
  }

  public void printPoints() {
    currentNode = header;
    System.out.println("Size: " + size + "\n");
    for (int i = 0; i < size; i++) {
      currentNode = currentNode.next;
      System.out.println(i + ": " + currentNode.point.toString());
    }
  }
}
