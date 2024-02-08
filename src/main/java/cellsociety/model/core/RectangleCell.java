package cellsociety.model.core;

import cellsociety.Point;

public class RectangleCell extends Cell {

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param row          is the x-coordinate of the cell on the 2-dimensional grid
   * @param col          is the y-coordinate of the cell on the 2-dimensional grid
   */
  public RectangleCell(int initialState, int row, int col) {
    super(initialState, row, col);
    final double[][] offsets = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    for (double[] offset : offsets) {
      addVertex(new Point(row + offset[0], col + offset[1]));
    }
    getCentroid();
  }
}
