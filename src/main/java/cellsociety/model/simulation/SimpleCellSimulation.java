package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.List;

public abstract class SimpleCellSimulation extends Simulation<Cell> {

  public SimpleCellSimulation(int row, int col, Neighborhood neighborhoodType,
      List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList,
        (ind -> new Cell(stateList.get(ind), ind / row, ind % col)));
  }
}
