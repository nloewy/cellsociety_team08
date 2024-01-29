package cellsociety.Model;

import java.util.*;

public interface Neighborhood {

  /**
   * Abstract class that represents a cell's neighborhood, which is a collection of points
   * surrounding a center point.
   *
   * @author Noah Loewy
   */


  /**
   * Different implementations will provide different ways to get the neighboring points of a given
   * point, based on differing definitions of what constitutes a "neighbor"
   *
   * @param p, a central point that we are aiming to get the neighbors of
   * @return List<Point>, a list of all points that could potentially represent neighboring cells of
   * point p, should those points be valid indices in the grid.
   */
  public List<Point> getNeighborCoordinates(Point p);
}
