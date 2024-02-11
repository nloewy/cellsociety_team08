package cellsociety.model.core.cell;

import cellsociety.model.core.shape.Shape;
import cellsociety.model.simulation.SugarSimulation;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

;

/**
 * Represents an extension of the Cell, and serves as the atomic unit of the Wator Simulation.
 * Contains extra instance variables that typical cells do not have, referencing energy and age.
 *
 * @author Noah Loewy
 */

public class SugarCell extends Cell<SugarCell> {


  private int myCurrentVision;
  private int myNextVision;
  private int myNextAgentSugar;
  private int sugarMetabolism;
  private int sugarGrowBackRate;

  private int myCurrentAgentSugar;

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
  public SugarCell(int initialState, int row, int col, Shape shapeType,
      Map<String, Integer> params) {
    super(initialState, row, col, shapeType);
    myCurrentVision = params.getOrDefault("vision", -1);
    myNextVision = PLACEHOLDER;
    myCurrentAgentSugar = params.getOrDefault("sugar", -1);
    myNextAgentSugar = PLACEHOLDER;
    sugarGrowBackRate = params.get("growBackRate");
    sugarMetabolism = params.getOrDefault("metabolism", -1);
  }

  public boolean agentHere() {
    return myCurrentAgentSugar > 0;
  }

  private boolean cellAvailable() {
    return myCurrentAgentSugar <= 0 && myNextAgentSugar < 0;
  }

  /**
   * Updates the state, age, and energy of the Wator Cell after the conclusion of the transition
   * function. This involves setting the "current" values to the "next" values, and settign the
   * "next" values to placeholder values.
   */
  @Override
  public void updateStates() {
    super.updateStates();
    myCurrentVision = myNextVision;
    myCurrentAgentSugar = myNextAgentSugar;
    myNextVision = PLACEHOLDER;
    myNextAgentSugar = PLACEHOLDER;
  }

  public void setNextStateAgentSugarVisionMetabolism(int state, int sugar, int vision,
      int metabolism) {
    setNextState(state);
    myNextAgentSugar = sugar;
    myNextVision = vision;
    sugarMetabolism = metabolism;

  }

  private Set<SugarCell> getVisibleNeighbors(double x, double y, int remainingVision,
      SugarCell ignoreMe) {
    Set<SugarCell> set = new HashSet<>();
    if (remainingVision == 0) {
      return set;
    }
    for (SugarCell neighbor : getNeighbors()) {
      if (neighbor.equals(ignoreMe)) {
        continue;
      }
      double dx = neighbor.getCentroid().getCol() - getCentroid().getCol();
      double dy = neighbor.getCentroid().getRow() - getCentroid().getRow();
      if ((dx == x && dy == y) || (x == Double.MAX_VALUE && y == Double.MAX_VALUE)) {
        set.add(neighbor);
        set.addAll(neighbor.getVisibleNeighbors(dx, dy, remainingVision - 1, ignoreMe));
      }
    }

    return set;
  }

  private SugarCell getFutureCell() {

    Set<SugarCell> neighborsInVision = getVisibleNeighbors(Double.MAX_VALUE, Double.MAX_VALUE,
        myCurrentVision, this);

    SugarCell bestOption = null;
    int bestVal = -1;
    for (SugarCell neighbor : neighborsInVision) {
      if (neighbor.cellAvailable()) {
        if (neighbor.getCurrentState() > bestVal) {
          bestVal = neighbor.getCurrentState();
          bestOption = neighbor;
        }
        if (neighbor.getCurrentState() == bestVal && (distance(neighbor) < distance(bestOption) ||
            (distance(neighbor) == distance(bestOption) && new Random().nextInt(2) == 1))) {
          bestOption = neighbor;
        }
      }
    }

    return bestOption;
  }

  @Override
  public void transition() {
    if (myCurrentAgentSugar > 0) {
      SugarCell nextCell = getFutureCell();
      if (nextCell != null) {
        int newAgentSugar = myCurrentAgentSugar + nextCell.getCurrentState() - sugarMetabolism;
        if (newAgentSugar > 0) {
          nextCell.setNextStateAgentSugarVisionMetabolism(0, newAgentSugar, myCurrentVision,
              sugarMetabolism);
        } else { //we assume that if an agent goes to a new cell and dies, that new cell is unavailable for the timestep
          nextCell.setNextStateAgentSugarVisionMetabolism(
              Math.min(SugarSimulation.MAX_AVAILABLE_SUGAR,
                  nextCell.getCurrentState() + sugarGrowBackRate),
              PLACEHOLDER,
              PLACEHOLDER, PLACEHOLDER);
        }
        setNextStateAgentSugarVisionMetabolism(
            Math.min(sugarGrowBackRate, SugarSimulation.MAX_AVAILABLE_SUGAR), PLACEHOLDER,
            PLACEHOLDER,
            PLACEHOLDER);
      } else {
        setNextStateAgentSugarVisionMetabolism(0,
            myCurrentAgentSugar + sugarGrowBackRate - sugarMetabolism,
            myCurrentVision, sugarMetabolism);
      }
    } else {
      setNextStateAgentSugarVisionMetabolism(
          Math.min(getCurrentState() + sugarGrowBackRate, SugarSimulation.MAX_AVAILABLE_SUGAR),
          PLACEHOLDER,
          PLACEHOLDER, PLACEHOLDER);

    }
  }

  public String getText() {
    if (myCurrentAgentSugar > 0) {
      return "\uD83C\uDF6C";
    }
    return "";
  }

  public int getCurrentSugar() {
    return myCurrentAgentSugar;
  }
}


