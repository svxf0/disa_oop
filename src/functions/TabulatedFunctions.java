package functions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class TabulatedFunctions {

  private TabulatedFunctions() {
  }

  public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
    if (function.getLeftDomainBorder() > leftX || function.getRightDomainBorder() < rightX) {
      throw new IllegalArgumentException();
    }
    ArrayTabulatedFunction points = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
    for (int i = 0; i < points.getPointCount(); i++) {
      points.setPointY(i, function.getFunctionValue(points.getPointX(i)));
    }
    return points;
  }



  public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
    String stringFunction = function.getStringFunction();
    try {
      for (int i = 0; i < stringFunction.length(); i++) {
        out.write(stringFunction, 0, stringFunction.length());
      }
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  private static TabulatedFunction stringToFunction(String string) {
    int size = string.charAt(0);
    string = string.substring(2);
    FunctionPoint[] points = new FunctionPoint[size];
    for (int i = 0; i <= size; i++) {
      points[i] = new FunctionPoint(string.charAt(4 * i), string.charAt(4 * i + 2));
    }
    return new ArrayTabulatedFunction(points);
  }

  /*public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException{
    try {
      TabulatedFunction tf = stringToFunction(in.read());
    }
  } */
}
