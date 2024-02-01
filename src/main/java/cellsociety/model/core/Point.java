package cellsociety.model.core;

public class Point {

  /**
   * Represents an x,y pairing in R^2 space
   *
   * @author Noah Loewy
   */
  private int myX;
  private int myY;

  /**
   * Constructs an object of type point
   *
   * @param x, coordinate on x axis
   * @param y, coordinate on y axis
   */
  public Point(int x, int y) {
    myX = x;
    myY = y;
  }

  /**
   * Creates a new point object that is a translation of a current point object
   *
   * @param dx, translation in x direction
   * @param dy, translation in y direction
   * @return Point, a *new* point that is the original point, translated.
   */
  public Point translate(int dx, int dy) {
    int newX = myX + dx;
    int newY = myY + dy;
    return new Point(newX, newY);
  }

  /**
   * Retrieves myX instance variable
   *
   * @return myX, the x coordinate of the Point on the 2d grid
   */
  public int getX() {
    return myX;
  }

  /**
   * Retrieves myY instance variable
   *
   * @return myY, the y coordinate of the Point on the 2d grid
   */
  public int getY() {
    return myY;
  }

  /**
   * Indicates whether another object is equal to *this* one
   *
   * @param other, an object in java
   * @return boolean, whether other object equals this instance
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (getClass() != other.getClass()) {
      return false;
    }
    Point otherPoint = (Point) other;
    return otherPoint.getX() == myX && otherPoint.getY() == myY;

  }

  public String toString(){
    return "("+myX + ", " + myY +")";
  }
  public int hashCode() {
    return 18 * myX + myY;
  }
}
