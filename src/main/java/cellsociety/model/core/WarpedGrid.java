package cellsociety.model.core;

import cellsociety.Point;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.List;

public class WarpedGrid extends Grid {

  public WarpedGrid(int rows, int cols, List<Cell> cellList) {
    super(rows,cols,cellList);
  }



  @Override
  public boolean containsVertex(Point p, List<Point> vertices) {
    if (super.containsVertex(p, vertices)){
      return true;
    }
    Point pNew = new Point((p.getX()+getNumCols())%getNumCols(),
        (p.getY()+getNumRows())%getNumRows());
    return super.containsVertex(pNew, vertices);
  }
}
