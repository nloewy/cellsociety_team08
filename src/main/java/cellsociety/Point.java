package cellsociety;

/**
 * Represents an x,y pairing in R^2 space
 *
 * @author Noah Loewy
 */

public class Point {

  private final double myRow;
  private final double myCol;
  private final double myColOffset;

  /**
   * Initializes a point object
   *
   * @param row    the row of the point on 2d plane
   * @param col    the column of the point on 2d plane
   * @param offset the x-offset of the point, if it is on a lattice grid
   */
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
   * @return boolean, whether other object equals this instance
   */

  public double getColOffset() {
    return myColOffset;
  }

  /**
   * Returns whether or not two points share a row and column
   *
   * @param other Java object
   * @return true if and only if this and other are both Point objects with equal row and columns
   * values.
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
    return otherPoint.getRow() == myRow && otherPoint.getCol() == myCol;

  }

  public String toString() {
    return myCol + ", " + myRow;
  }


}

