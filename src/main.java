package com.company.functions;

import functions.*;

public class Main {

  public static void main(String[] args) {
    double[] dots = {0.0, 1.0, 2.0};
    TabulatedFunction table = new TabulatedFunction(0, 5, dots);
    FunctionPoint[] points = table.getPoints();
    table.printPoints();
    System.out.println(table.getFunctionValue(2.5));
    table.setPoint(2, new FunctionPoint(6.0, 4.0));
    table.printPoints();
    table.addPoint(new FunctionPoint(7.5, 14));
    table.printPoints();
  }
}

