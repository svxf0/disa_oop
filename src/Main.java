import functions.FunctionPoint;
import functions.TabulatedFunction;

public class Main {
  public static void main(String[] args) {
    double[] x = new double[11];
    double[] y = new double[11];

    for (int i = -5; i <= 5; i++) {
      x[i + 5] = i;
      y[i + 5] = i * i;
    }

    TabulatedFunction function = new TabulatedFunction(-5, 5, y);

    for (double i = -5.0; i <= 5; i += 0.7) {
      System.out.println(String.format("f(%.2f) = %.2f", i, function.getFunctionValue(i)));
    }
    System.out.println();

    function.removePoint(3); // -2
    function.removePoint(3); // -1
    function.removePoint(3); // 0
    function.removePoint(3); // 1
    function.removePoint(3); // 2

    for (double i = -5.0; i <= 5; i += 0.7) {
      System.out.println(String.format("f(%.2f) = %.2f", i, function.getFunctionValue(i)));
    }
    System.out.println();

    function.addPoint(new FunctionPoint(0, 0));

    for (double i = -5.0; i <= 5; i += 0.7) {
      System.out.println(String.format("f(%.2f) = %.2f", i, function.getFunctionValue(i)));
    }
    System.out.println();
    /*double leftX = 0.0;
    double rightX = 0.4;
    int pointsCount = 4;
    double[] values = {};
    TabulatedFunction function = new TabulatedFunction(leftX, rightX, pointsCount);
    for (int i = 0; i < function.getPointsCount(); i++) {
      System.out.println(function.getPointX(i) + " " + function.getPointY(i));
    }
    double x = 0.11;
    double y = 2.4;
    for (int i = 0; i < 16; i++) {
      function.addPoint(new FunctionPoint(x, y));
      x += i;
      y += i;
    }
    for (int i = 0; i < function.getPointsCount(); i++) {
      System.out.println(function.getPointX(i) + " " + function.getPointY(i));
    }*/
  }
}
