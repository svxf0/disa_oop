package functions.basic;

import functions.Function;

public class Cos extends TrigonometricFunction implements Function {

  public double getFunctionValue(double x) {
    return Math.cos(x);
  }

}
