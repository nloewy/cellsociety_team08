package cellsociety.model.core;

import cellsociety.Point;
import java.util.ArrayList;
import java.util.List;

public class HexagonCell extends Cell{

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   */
  public HexagonCell(int initialState, int x, int y) {
    super(initialState, x, y);
    List<Point> myVertices = new ArrayList<>();
    myVertices.add(new Point(x,y));
    myVertices.add(new Point(x+1,y));
    myVertices.add(new Point(x,y+1));
    myVertices.add(new Point(x+1,y+1));
  }
}
