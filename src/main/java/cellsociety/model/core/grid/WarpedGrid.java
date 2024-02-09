package cellsociety.model.core.grid;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import java.util.List;

/**
 * The WarpedGrid, an extension of the Generic Grid, represents the Collection of Generic Cells in
 * our simulation, Unlike the finite general grid, this grid "warps" around, in the sense that if
 * a cell goes beyond the boundaries of the grid, it reappears on the opposite side, creating a
 * toroidal (wrap-around) effect. This is applicable with all cell shapes and neighborhood types.
 *
 * @author Noah Loewy
 */
public class WarpedGrid<T extends Cell<T>> extends Grid<T> {

  public WarpedGrid(int rows, int cols, List<T> cellList) {
    super(rows, cols, cellList);
  }


  /**
   * Checks if a given vertex point is contained within the specified list of vertices, taking into
   * account the toroidal (wrap-around) effect of the grid.
   * <p>
   * This method first checks if the vertex is contained within the provided list of vertices using
   * the standard containsVertex method of the superclass. If the vertex is found, it returns true
   * immediately. If the vertex is not found within the original list, it applies translates the v
   * vertex to simulate the toroidal behavior of the grid. This is especially useful for irregular
   * lattice structures like hexagons, where the wrapping behavior may not be straightforward. The
   * shape-aspect is handled by employing an offset, stored within the vertex during its
   * initialization. If any of the translated vertices are found within the provided list of
   * vertices, indicating that the original vertex wraps around the grid boundaries, the method
   * returns true. Otherwise, it returns false, indicating that the vertex is not contained within
   * the grid or its wrap-around positions.
   *
   * @param vtx       The vertex point to be checked.
   * @param vertices  The list of vertices to check against.
   * @return true if the vertex is contained within the specified list of vertices or its wrap-around
   * positions; false otherwise.
   */
  @Override
  public boolean containsVertex(Point vtx, List<Point> vertices) {
    if (super.containsVertex(vtx, vertices)) {
      return true;
    }
    final double[][] translations = {{getNumRows(), vtx.getColOffset()}, {-getNumRows(),
        vtx.getColOffset()}, {0, getNumCols()}, {0, -getNumCols()}};
    for (int i = 0; i < translations.length; i++) {
      if (super.containsVertex(vtx.translate(translations[i][0], translations[i][1]), vertices)) {
        return true;
      }
    }
    return false;
  }
}
