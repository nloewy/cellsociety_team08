package cellsociety.model.core.cell;

import cellsociety.model.core.shape.CellShape;
import cellsociety.model.simulation.PercolationSimulation;
import java.util.Map;

public class PercolationCell extends Cell {

  private double percolatedNeighbors;

  public PercolationCell(int initialState, int row, int col, CellShape shapeType,
      Map<String, Integer> params) {
    super(initialState, row, col, shapeType);
    percolatedNeighbors = params.get("percolatedNeighbors");
  }

  /**
   * Handles transition of open cell in PercolationSimulation. Open cells with at least
   * neighersPercolatedRequired will become percolated, and otherwise will remain open.
   */
  private void handleOpenCell() {
    int numPercolatedNeighbors = countNeighborsInState(PercolationSimulation.PERCOLATED);
    if (numPercolatedNeighbors >= percolatedNeighbors) {
      setNextState(PercolationSimulation.PERCOLATED);
    } else {
      setNextState(PercolationSimulation.OPEN);
    }
  }

  @Override
  public void transition() {
    if (getCurrentState() == PercolationSimulation.OPEN) {
      handleOpenCell();
    } else {
      setNextState(getCurrentState());
    }
  }

}
