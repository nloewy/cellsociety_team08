package cellsociety.model.core.cell;

import static java.lang.Math.random;

import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.FireSimulation;
import java.util.Map;

/**
 * Represents an extension of the Cell, and serves as the atomic unit of the Fire simulation.
 * Contains private methods that allow for the transition of Fire Cell objects at each timestep.
 *
 * @author Noah Loewy
 */

public class FireCell extends Cell {

  /**
   * Probability an empty Cell transitions to a tree cell
   */
  private final double probTreeCreated;
  /**
   * Probability an tree Cell with insufficient burning neighbors transitions to a burning cell
   */
  private final double probTreeIgnites;
  /**
   * Number of burning neighbors a tree cell requires to transition to a burning cell.
   */
  private final double neighborsToIgnite;

  /**
   * Constructs a Fire Cell object for the Catching Fire simulation
   *
   * @param initialState the integer representation of the cell's current state
   * @param row          the row the cell is positioned at as represented on a 2D coordinate grid
   * @param col          the column the cell is positioned at as represented on a 2D coordinate
   *                     grid
   * @param shapeType    the shape of a cell, as represented on a 2D coordinate grid
   * @param params       map of string parameter names to their values. Description of parameters
   *                     can be found at te declaration of the instance variables.
   */
  public FireCell(int initialState, int row, int col, Shape shapeType,
      Map<String, Double> params) {
    super(initialState, row, col, shapeType);
    probTreeCreated = params.get("probTreeCreated");
    probTreeIgnites = params.get("probTreeIgnites");
    neighborsToIgnite = params.get("neighborsToIgnite");
  }


  /**
   * Handles transition of empty cell in FireSimulation. Empty cells transition to trees with
   * probability probTreeCreated, and remain empty with probability 1 - probTreeCreated
   */
  private void handleEmptyCell() {
    if (random() <= probTreeCreated) {
      setNextState(FireSimulation.TREE);
    } else {
      setNextState(FireSimulation.EMPTY);
    }
  }

  /**
   * Handles transition of tree cell. Tree cells with at least neighborsToIgnite burning neighbors
   * will always burn. Tree cells that do not meet the required amount of burning neighbors will
   * burn with probability probTreeIgnites, and remain trees with probability 1 - probTreeIgnites
   */

  private void handleTreeCell() {
    int burningNeighbors = countNeighborsInState(FireSimulation.BURNING);
    if (burningNeighbors >= neighborsToIgnite || random() <= probTreeIgnites) {
      setNextState(FireSimulation.BURNING);
    } else {
      setNextState(FireSimulation.TREE);
    }
  }

  /**
   * Represents a timestep update for a FireCell. Calls the proper helper function for transitioning
   * based on the state of the Fire Cell being updated.
   */
  @Override
  public void transition() {
    switch (getCurrentState()) {
      case FireSimulation.EMPTY: {
        handleEmptyCell();
        break;
      }
      case FireSimulation.BURNING: {
        setNextState(FireSimulation.EMPTY);
        break;
      }
      case FireSimulation.TREE: {
        handleTreeCell();
        break;
      }
      default:
        break;
      //TODO: check if exception needed
    }
  }
}

