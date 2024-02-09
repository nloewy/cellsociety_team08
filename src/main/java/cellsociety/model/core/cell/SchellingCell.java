package cellsociety.model.core.cell;


import cellsociety.model.core.shape.CellShape;
import cellsociety.model.simulation.SchellingSimulation;
import java.util.List;
import java.util.Map;

public class SchellingCell extends Cell {

  /**
   * Proportion of a cell's non-empty neighbors that share a state with them
   */
  private double proportionNeededToStay;

  /**
   * Constructs a Schelling Cell object for the Schelling's Model of Segregation simulation
   *
   * @param initialState  the integer representation of the cell's current state
   * @param row           the row the cell is positioned at as represented on a 2D coordinate grid
   * @param col           the column the cell is positioned at as represented on a 2D coordinate
   *                      grid
   * @param shapeType     the shape of a cell, as represented on a 2D coordinate grid
   * @param params        map of string parameter names to their values. Description of parameters
   *                      can be found at te declaration of the instance variables.
   */
  public SchellingCell(int initialState, int row, int col, CellShape shapeType,
      Map<String, Double> params) {
    super(initialState, row, col, shapeType);
    proportionNeededToStay = params.get("proportionNeededToStay");
  }

  /**
   * Handles transition of cell Schelling's Segregation simulation. Calls the proper helper function
   * for transitioning based on the state of the Schelling Cell being updated.
   */
  @Override
  public void transition() {
    if (getCurrentState() == SchellingSimulation.EMPTY) {
      setNextState(SchellingSimulation.TEMP_EMPTY);
    }
    handleDemographicCell();
  }

  /**
   * Handles transition of non-empty cell in Schelling's Simulation. If the cell is satisfied with
   * its current location, it will remain in place, and otherwise will be set to a temporary state,
   * that indicates it would like to be moved to a vacant cell
   */
  private void handleDemographicCell() {
    if (isSatsfied()) {
      setNextState(SchellingSimulation.TEMP_TO_MOVE);
    } else {
      setNextState(getCurrentState());
    }
  }

  /**
   * Indicates whether or not a current cell is satisfied with their current state. Cells are
   * satisfied if and only if they are surrounded entirely by empty cells, or if the proportion of
   * non-empty neighbors that are in the same demographic as them is no less than
   * proportionNeededToStay.
   *
   * @return boolean representing if current cell is satisfied
   */
  private boolean isSatsfied() {
    List<SchellingCell> neighbors = getNeighbors();
    int totalNeighbors = neighbors.size();
    int numEmptyNeighbors = countNeighborsInState(SchellingSimulation.EMPTY);
    int numNeighborsSameState = countNeighborsInState(getCurrentState());
    return (totalNeighbors == numEmptyNeighbors
        || (double) numNeighborsSameState / (totalNeighbors - numEmptyNeighbors)
        >= proportionNeededToStay);
  }
}

