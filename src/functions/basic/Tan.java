package functions.basic;

import functions.Function;

public class Tan extends TrigonometricFunction implements Function {

  public double getFunctionValue(double x) {
    return Math.tan(x);
  }

}
