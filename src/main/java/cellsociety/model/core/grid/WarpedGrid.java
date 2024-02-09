package cellsociety.model.core.grid;

import cellsociety.Point;
import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.grid.Grid;
import java.util.List;

public class WarpedGrid extends Grid {

  public WarpedGrid(int rows, int cols, List<Cell> cellList) {
    super(rows, cols, cellList);
  }


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
