package cellsociety.model.core.cell;


import cellsociety.model.core.shape.CellShape;
import cellsociety.model.simulation.SchellingSimulation;
import java.util.List;
import java.util.Map;

public class SchellingCell extends Cell {

  private double proportionNeededToStay;

  public SchellingCell(int initialState, int row, int col, CellShape shapeType,
      Map<String, Double> params) {
    super(initialState, row, col, shapeType);
    proportionNeededToStay = params.get("proportionNeededToStay");
  }

  @Override
  public void updateStates() {
    super.updateStates();
  }

  /**
   * Given a cell in either group A or group B, places the cell in either myCellsToMove, or updates
   * its future state to its current-state, depending on the state-makeup of its neighbors
   */
  @Override
  public void transition() {
    if (getCurrentState() == SchellingSimulation.EMPTY) {
      setNextState(SchellingSimulation.TEMP_EMPTY);
    }
    handleDemographicCell();
  }

  private void handleDemographicCell() {
    if (isNotSatsfied()) {
      setNextState(SchellingSimulation.TEMP_TO_MOVE);
    } else {
      setNextState(getCurrentState());
    }
  }

  private boolean isNotSatsfied() {
    List<SchellingCell> neighbors = getNeighbors();
    int totalNeighbors = neighbors.size();
    int numEmptyNeighbors = countNeighborsInState(SchellingSimulation.EMPTY);
    int numNeighborsSameState = countNeighborsInState(getCurrentState());
    return (totalNeighbors != numEmptyNeighbors
        && (double) numNeighborsSameState / (totalNeighbors - numEmptyNeighbors)
        < proportionNeededToStay);
  }
}

