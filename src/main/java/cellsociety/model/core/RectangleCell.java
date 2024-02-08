package cellsociety.model.core;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

public class RectangleCell extends Cell{

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   */
  public RectangleCell(int initialState, int row, int col) {
    super(initialState, row, col);
    List<Point> myVertices = getVertices();
    myVertices.add(new Point(row,col));
    myVertices.add(new Point(row+1,col));
    myVertices.add(new Point(row,col+1));
    myVertices.add(new Point(row+1,col+1));
    getCentroid();
  }
}
