package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.core.WatorCell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WatorSimulation extends Simulation<WatorCell> {

  /**
   * This cellular automata simulation represents the Predator-Prey simulaton developed by Alexander
   * K. Dewdney.
   * <p>
   * author @noah loewy
   */
  public static final int EMPTY = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int[] UPDATE_ORDER = {SHARK, FISH, EMPTY};
  private int fishAgeOfReproduction;
  private int sharkAgeOfReproduction;
  private int fishEnergyBoost;
  private int initialEnergy;

  /**
   * Initializes a WatorSimulation object
   *
   * @param row,              the number of rows in the 2-dimensional grid
   * @param col,              the number of columns in the 2-dimensional grid
   * @param neighborhoodType, the definition of neighbors
   * @param stateList,        a list of the integer representation of each cells state, by rows,
   *                          then cols
   */
  public WatorSimulation(int row, int col, Neighborhood neighborhoodType, List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList, (ind -> new WatorCell(stateList.get(ind),
        ind / row, ind % col, 2, 0)));
    fishAgeOfReproduction = 4;
    sharkAgeOfReproduction = 4;
    initialEnergy = 2;
    fishEnergyBoost = 4;
  }

  /**
   * Filters a list of WatorCell objects to retreive all Cells of a certain state
   *
   * @param cellList a list of valid WatorCell objects
   * @param state    target state in filtering operation
   * @return a (copy) of a filtered list of all cells in cellList with a currentState equal to state
   */
  private List<WatorCell> getCellsOfState(List<WatorCell> cellList, int state) {
    List<WatorCell> ret = new ArrayList<>();
    for (WatorCell cell : cellList) {
      if (cell.getCurrentState() == state) {
        ret.add(cell);
      }
    }
    return ret;
  }

  /**
   * Updates a cell to put it in an empty state
   *
   * @param cell an arbitrary cell in the simulation that is transitioning to empty
   */
  private void fillEmptyCell(WatorCell cell) {
    cell.updateStateEnergyAge(EMPTY, -1, -1);
  }

  /**
   * Allows for a WatorCell in state fish to reproduce, going back to age 0
   *
   * @param currentCell the cell of the parent fish is originally
   * @param nextCell    the cell where the parent fish moves to after reproduction
   */
  private void reproduceFish(WatorCell currentCell, WatorCell nextCell) {
    currentCell.updateStateEnergyAge(FISH, -1, 0);
    nextCell.updateStateEnergyAge(FISH, -1, 0);
  }

  /**
   * Increases the age of a cell by 1, keeping other parameters constant
   *
   * @param currentCell the fish cell whose age will update by 1
   */
  private void increaseFishAge(WatorCell currentCell) {
    currentCell.updateStateEnergyAge(FISH, currentCell.getAge() + 1, -1);
  }

  /**
   * Handles movement of shark from one cell to another. This assumes the cell the shark is
   * attempting to move to is already determined. Shark will reproduce if necessary.
   *
   * @param currentCell the cell of the shark originally
   * @param nextCell    the cell the shark is attempting to move to
   */
  private void handleSharkMoveToEmptySpace(WatorCell currentCell, WatorCell nextCell) {
    if (nextCell.getNextState() == SHARK) { //another shark is already moving to nextCell
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1);
    } else {
      nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1, currentCell.getAge() + 1);
      if (currentCell.getAge() >= sharkAgeOfReproduction) {
        currentCell.updateStateEnergyAge(SHARK, initialEnergy, 0); //create shark
        nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1, 0); //reproducing Shark
      } else {
        fillEmptyCell(currentCell);
      }
    }
  }

  /**
   * Handles shark's hunting of fish. Shark will move to cell with fish and eat the fish if no cell
   * is already planning to occupy that cell. Shark will reproduce if necessary
   *
   * @param currentCell the cell where the predator (shark) is first located
   * @param nextCell    the cell where the prey (fish) is first located, and where the shark will
   *                    move
   */
  private void handleSharkEatFish(WatorCell currentCell, WatorCell nextCell) {
    if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH) {
      //another shark or fish is already moving to nextCell
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1);
    } else {
      nextCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() + fishEnergyBoost,
          currentCell.getAge() + 1);
      if (currentCell.getAge() >= sharkAgeOfReproduction) {
        currentCell.updateStateEnergyAge(SHARK, initialEnergy, 0);
      } else {
        fillEmptyCell(currentCell);
      }
    }
  }

  /**
   * Handles case where a shark is surrounded by sharks (cannot move). Shark ages and loses energy.
   *
   * @param currentCell the cell holding a shark that is unable to move.
   */
  private void handleSharkCantMove(WatorCell currentCell) {
    if (currentCell.getEnergy() > 1) {
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1);
    } else {
      fillEmptyCell(currentCell);
    }
  }

  /**
   * Specific transition function for a fish cell in Wator Simulation. Calls the proper helper
   * function for transitioning based on the current state of neighbors.
   *
   * @param currentCell the fish cell trying to transition
   */
  private void updateFish(WatorCell currentCell) {
    List<WatorCell> neighbors = getNeighbors(currentCell);
    List<WatorCell> emptyNeighbors = getCellsOfState(neighbors, EMPTY);
    Collections.shuffle(emptyNeighbors);
    if (emptyNeighbors.isEmpty()) {
      increaseFishAge(currentCell);
    } else {
      WatorCell nextCell = emptyNeighbors.get(0);
      if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH
          || currentCell.getAge() < fishAgeOfReproduction) {
        increaseFishAge(currentCell);
      } else {
        reproduceFish(currentCell, nextCell);
      }
    }
  }

  /**
   * Specific transition function for a shark cell in Wator Simulation. Calls the proper helper
   * function for transitioning based on the current state of neighbors.
   *
   * @param currentCell the fish cell trying to transition
   */
  private void updateShark(WatorCell currentCell) {
    List<WatorCell> neighbors = getNeighbors(currentCell);
    List<WatorCell> emptyNeighbors = getCellsOfState(neighbors, EMPTY);
    List<WatorCell> fishNeighbors = getCellsOfState(neighbors, FISH);

    Collections.shuffle(emptyNeighbors);
    Collections.shuffle(fishNeighbors);

    if (fishNeighbors.isEmpty() && emptyNeighbors.isEmpty()) {
      handleSharkCantMove(currentCell);
    } else if (!fishNeighbors.isEmpty()) {
      handleSharkEatFish(currentCell, fishNeighbors.get(0));
    } else {
      if (currentCell.getEnergy() <= 1) {
        fillEmptyCell(currentCell);
      } else {
        handleSharkMoveToEmptySpace(currentCell, emptyNeighbors.get(0));
      }
    }
  }


  /**
   * Transition function for Wator World. Iterates through each cell and calls proper cell-specific
   * transition function based on the state of the particular cell
   */
  @Override
  public void transitionFunction() {
    for (int cellToUpdate : UPDATE_ORDER) {
      Iterator<WatorCell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        WatorCell currentCell = gridIterator.next();
        if (currentCell.getNextState() == Cell.PLACEHOLDER &&
            currentCell.getCurrentState() == cellToUpdate) {
          switch (cellToUpdate) {
            case EMPTY: {
              currentCell.setNextState(EMPTY);
            }
            case FISH: {
              updateFish(currentCell);
            }
            case SHARK: {
              updateShark(currentCell);
            }
          }
        }
      }
    }
  }
}







