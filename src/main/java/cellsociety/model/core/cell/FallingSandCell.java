package cellsociety.model.core.cell;

import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.FallingSandSimulation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Represents an extension of the Cell, and serves as the atomic unit of the Falling Sand
 * simulation. Contains private methods that allow for the transition of Falling Sand Cell objects
 * at each timestep.
 *
 * @author Noah Loewy
 */
public class FallingSandCell extends Cell<FallingSandCell> {

  /**
   * Constructs a Percolation Cell object for the Percolation simulation
   *
   * @param initialState the integer representation of the cell's current state
   * @param row          the row the cell is positioned at as represented on a 2D coordinate grid
   * @param col          the column the cell is positioned at as represented on a 2D coordinate
   *                     grid
   * @param shapeType    the shape of a cell, as represented on a 2D coordinate grid
   */
  public FallingSandCell(int initialState, int row, int col, Shape shapeType) {
    super(initialState, row, col, shapeType);
  }


  /**
   * Handles transition of sand cell in Falling Sand Cell percolation. These sand cells will move
   * downward, replacing empty cells or water cells when possible, each timestep.
   */
  private void handleSandCell() {
    FallingSandCell bestOption = null;
    double bestSlope = 0;
    for (FallingSandCell cell : getNeighbors()) {
      double newSlope = (cell.getCentroid().getRow() - getCentroid().getRow()) /
          (cell.getCentroid().getCol() - getCentroid().getCol());
      if (cell.getCentroid().getRow() > getCentroid().getRow()
          && Math.abs(newSlope) > bestSlope) {
        bestOption = cell;
        bestSlope = Math.abs(newSlope);

      }
    }
    if (bestOption == null) {
      setNextState(getCurrentState());
      return;
    }
    if (bestOption.getCurrentState() == FallingSandSimulation.WATER) {
      setNextState(FallingSandSimulation.WATER);
      bestOption.setNextState(FallingSandSimulation.SAND);
    } else if (bestOption.getCurrentState() == FallingSandSimulation.EMPTY) {
      bestOption.setNextState(FallingSandSimulation.SAND);
      setNextState(FallingSandSimulation.EMPTY);
      setCurrentState(FallingSandSimulation.EMPTY);

    } else {
      setNextState(getCurrentState());
    }
  }

  /**
   * Handles transition of water cell, so it randomly moves horizontally or downward into an empty
   * space
   */
  private void handleWaterCell() {
    List<FallingSandCell> options = new ArrayList<>();
    for (FallingSandCell cell : getNeighbors()) {
      if (cell.getCentroid().getRow() >= getCentroid().getRow()) {
        if (cell.getCurrentState() == FallingSandSimulation.EMPTY
            && cell.getNextState() == PLACEHOLDER) {
          options.add(cell);
        }
      }
    }
    Collections.shuffle(options);
    int index = 0;
    while (index < options.size() && options.get(index).getNextState() != PLACEHOLDER) {
      index++;
    }
    if (index == options.size()) {
      setNextState(getCurrentState());
      return;
    }
    FallingSandCell nextCell = options.get(index);
    if (nextCell.getNextState() == PLACEHOLDER) {
      if (nextCell.getCurrentState() == FallingSandSimulation.EMPTY) {
        nextCell.setNextState(FallingSandSimulation.WATER);
        setCurrentState(FallingSandSimulation.EMPTY);
      }
    }

  }


  /**
   * Represents a timestep update for a Falling Sand Cell. Calls the proper helper function for
   * transitioning based on the state of the Falling Sand Cell being updated.
   */
  @Override
  public void transition() {
    System.out.print(getCurrentState());
    if (getCurrentState() == FallingSandSimulation.SAND) {
      handleSandCell();
    } else if (getCurrentState() == FallingSandSimulation.WATER) {
      handleWaterCell();
    } else {
      setNextState(getCurrentState());
    }
    System.out.println("=>" + getNextState());
  }

}
