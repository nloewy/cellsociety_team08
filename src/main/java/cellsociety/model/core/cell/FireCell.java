package cellsociety.model.core.cell;

import static java.lang.Math.random;

import cellsociety.model.core.cell.Cell;
import cellsociety.model.core.shape.CellShape;
import cellsociety.model.simulation.FireSimulation;
import java.util.Map;


public class FireCell extends Cell {

  private double probTreeCreated;
  private double probTreeIgnites;
  private double neighborsToIgnite;

  public FireCell(int initialState, int row, int col, CellShape shapeType,
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
   * Handles transition of tree cell in FireSimulation. Tree cells with at least neighborsToIgnite
   * burning neighbors will always burn. Tree cells that do not meet the required amount of burning
   * neighbors will burn with probability probTreeIgnites, and remain trees with probability 1 -
   * probTreeIgnites
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
   * Iterates through each cell, and calls the proper helper function for transitioning based on the
   * current state of the cell in the Fire Simulation
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

