package functions;

public interface TabulatedFunction extends Function {

  public int getPointCount();

  public double getPointX(int Index);

  public double getPointY(int index);

  public FunctionPoint getPoint(int index);

  public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

  public void setPointX(int i, double x) throws InappropriateFunctionPointException;

  public void setPointY(int index, double y);

  public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

  public void deletePoint(int index);

  public String toString();

  public String getStringFunction();
}
