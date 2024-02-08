package cellsociety.model.core;

import cellsociety.Point;

public class HexagonCell extends Cell {

  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param row          is the x-coordinate of the cell on the 2-dimensional grid
   * @param col          is the y-coordinate of the cell on the 2-dimensional grid
   */
  public HexagonCell(int initialState, int row, int col) {
    super(initialState, row, col);
    double shapeOffset = .5;
    double currOffset;
    if (row % 2 == 1) {
      currOffset = .5;
    } else {
      currOffset = 0.0;
    }
    final double[][] offsets = {{.25, 0}, {1, 0}, {1.25, .5}, {1, 1}, {.25, 1}, {1, .5}};
    for (double[] offset : offsets) {
      addVertex(new Point(row + offset[0], col + offset[1] + currOffset, shapeOffset));
    }
  }
}
