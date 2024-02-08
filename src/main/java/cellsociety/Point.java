package cellsociety;

import static java.lang.Math.round;

/**
 * Represents an x,y pairing in R^2 space
 *
 * @author Noah Loewy
 */

public class Point {

  private double myRow;
  private double myCol;
  private double myColOffset = 0;

  public Point(double row, double col, double offset) {
    myRow = row;
    myCol = col;
    myColOffset = offset;
  }

  /**
   * Constructs an object of type point
   *
   * @param row, row coordinate
   * @param col, col coordinate
   */
  public Point(double row, double col) {
    this(row, col, 0.0);
  }


  /**
   * Creates a new point object that is a translation of a current point object
   *
   * @param deltaRow, translation in row direction
   * @param deltaCol, translation in col direction
   * @return Point, a *new* point that is the original point, translated.
   */
  public Point translate(double deltaRow, double deltaCol) {
    double newRow = myRow + deltaRow;
    double newCol = myCol + deltaCol;
    return new Point(newRow, newCol);
  }

  /**
   * Retrieves myX instance variable
   *
   * @return myX, the x coordinate of the Point on the 2d grid
   */
  public double getRow() {
    return myRow;
  }

  /**
   * Retrieves myY instance variable
   *
   * @return myY, the y coordinate of the Point on the 2d grid
   */
  public double getCol() {
    return myCol;
  }

  /**
   * Indicates whether another object is equal to *this* one
   *
   * @param other, an object in java
   * @return boolean, whether other object equals this instance
   */

  public double getColOffset() {
    return myColOffset;
  }

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
    return otherPoint.getRow() == myRow && otherPoint.getCol() == myCol;

  }

  /**
   * Retrieves the hashcode of a Point object
   *
   * @return integer hashcode
   */
  @Override
  public int hashCode() {
    return (int) round(18 * myRow + myCol);
  }

  public String toString() {
    return "(row:" + myRow + ",col" + myCol + ")";
  }
}
