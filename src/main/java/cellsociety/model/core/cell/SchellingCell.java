package cellsociety.model.core.cell;


import cellsociety.model.core.shape.CellShape;
import cellsociety.model.simulation.SchellingSimulation;
import java.util.List;
import java.util.Map;

public class SchellingCell extends Cell {

  private double proportionNeededToStay;
  private boolean emptyList;
  private boolean toMove;

  public SchellingCell(int initialState, int row, int col, CellShape shapeType,
      Map<String, Double> params) {
    super(initialState, row, col, shapeType);
    proportionNeededToStay = params.get("proportionNeededToStay");
    emptyList = false;
    toMove = false;
  }

  @Override
  public void updateStates() {
    super.updateStates();
    toMove = false;
    emptyList = false;
  }

  /**
   * Given a cell in either group A or group B, places the cell in either myCellsToMove, or updates
   * its future state to its current-state, depending on the state-makeup of its neighbors
   */
  @Override
  public void transition() {
    if (getCurrentState() == SchellingSimulation.EMPTY) {
      emptyList = true;
    }
    handleDemographicCell();
  }

  public boolean getToEmptyList() {
    return emptyList;
  }

  public boolean getToMove() {
    return toMove;
  }

  private void handleDemographicCell() {
    if (isNotSatsfied()) {
      toMove = true;
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

