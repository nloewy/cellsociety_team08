package cellsociety.model.core;

import cellsociety.Point;
import java.util.List;

public interface CellShape {

  public List<Point> getVertices(int row, int col);

}
