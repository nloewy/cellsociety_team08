package cellsociety.model.core.cell;


import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.GameOfLifeSimulation;
import cellsociety.model.simulation.SchellingSimulation;
import java.util.List;
import java.util.Map;

public class SchellingCell extends Cell<SchellingCell> {

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
   *                      can be found at the declaration of the instance variables.
   */
  public SchellingCell(int initialState, int row, int col, Shape shapeType,
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
      if(isSatsfied(SchellingSimulation.GROUP_A)){
        setNextState(SchellingSimulation.TEMP_EMPTY_A);
      }
      else if(isSatsfied(SchellingSimulation.GROUP_B)) {
        setNextState(SchellingSimulation.TEMP_EMPTY_B);
      }
    }
    else{
    handleDemographicCell();
    }
  }

  /**
   * Handles transition of non-empty cell in Schelling's Simulation. If the cell is satisfied with
   * its current location, it will remain in place, and otherwise will be set to a temporary state,
   * that indicates it would like to be moved to a vacant cell
   */
  private void handleDemographicCell() {
    if (isSatsfied(getCurrentState())) {
      setNextState(getCurrentState());
    } else {
      setNextState(SchellingSimulation.TEMP_TO_MOVE);
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
  private boolean isSatsfied(int state) {
    List<SchellingCell> neighbors = getNeighbors();
    int totalNeighbors = neighbors.size();
    int numEmptyNeighbors = countNeighborsInState(SchellingSimulation.EMPTY);
    int numNeighborsSameState = countNeighborsInState(state);
    return (totalNeighbors == numEmptyNeighbors
        || (double) numNeighborsSameState / (totalNeighbors - numEmptyNeighbors)
        >= proportionNeededToStay);
  }
  public String getText() {
    return "";
  }

}

