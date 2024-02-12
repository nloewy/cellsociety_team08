package cellsociety.model.core.cell;

import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.PercolationSimulation;
import java.util.Map;

/**
 * Represents an extension of the Cell, and serves as the atomic unit of the Percolation simulation.
 * Contains private methods that allow for the transition of Percolation Cell objects at each
 * timestep.
 *
 * @author Noah Loewy
 */
public class PercolationCell extends Cell<PercolationCell> {

  /**
   * Number of percolated neighbors required for open cell to percolate
   */
  private double percolatedNeighbors;

  /**
   * Constructs a Percolation Cell object for the Percolation simulation
   *
   * @param initialState the integer representation of the cell's current state
   * @param row          the row the cell is positioned at as represented on a 2D coordinate grid
   * @param col          the column the cell is positioned at as represented on a 2D coordinate
   *                     grid
   * @param shapeType    the shape of a cell, as represented on a 2D coordinate grid
   * @param params       map of string parameter names to their values. Description of parameters
   *                     can be found at the declaration of the instance variables.
   */
  public PercolationCell(int initialState, int row, int col, Shape shapeType,
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

  /**
   * Represents a timestep update for a PercolationCell. Calls the proper helper function for
   * transitioning based on the state of the Percolation Cell being updated.
   */
  @Override
  public void transition() {
    if (getCurrentState() == PercolationSimulation.OPEN) {
      handleOpenCell();
    } else {
      setNextState(getCurrentState());
    }
  }

  public void setParams(Map<String, Double> params) {
    percolatedNeighbors = (int) Math.floor(params.get("percolatedNeighbors"));
  }
}
