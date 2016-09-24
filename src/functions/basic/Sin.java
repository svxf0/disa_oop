package functions.basic;

import functions.Function;

public class Sin extends TrigonometricFunction implements Function {

  public double getFunctionValue(double x) {
    return Math.sin(x);
  }

}
