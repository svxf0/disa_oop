package functions.meta;

import functions.Function;

/**
 * Created by asdisa on 21.09.16.
 */
public class Sum implements Function {

  private Function f1, f2;

  public Sum(Function f1, Function f2) {
    this.f1 = f1;
    this.f2 = f2;
  }
  public double getLeftDomainBorder() {
    return Math.min(f1.getLeftDomainBorder(), f2.getLeftDomainBorder());
  }

  public double getRightDomainBorder() {
    return Math.min(f1.getRightDomainBorder(), f2.getRightDomainBorder());
  }

  public double getFunctionValue(double x) {
    return f1.getFunctionValue(x) + f2.getFunctionValue(x);
  }

}
