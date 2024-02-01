package cellsociety.model.simulation;

import cellsociety.model.core.Cell;
import cellsociety.model.core.WatorCell;
import cellsociety.model.neighborhood.Neighborhood;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WatorSimulation extends Simulation<WatorCell> {

  public static final int EMPTY = 0;
  public static final int FISH = 1;
  public static final int SHARK = 2;
  public static final int[] UPDATE_ORDER = {SHARK, FISH, EMPTY};
  private int fishAgeOfReproduction;
  private int sharkAgeOfReproduction;
  private int fishEnergyBoost;
  private int initialEnergy;

  public WatorSimulation(int row, int col, Neighborhood neighborhoodType, List<Integer> stateList) {
    super(row, col, neighborhoodType, stateList, (ind -> new WatorCell(stateList.get(ind),
        ind / row, ind % col, 2, 0)));
    fishAgeOfReproduction = 4;
    sharkAgeOfReproduction = 4;
    initialEnergy = 2;
    fishEnergyBoost = 4;
  }

  private List<WatorCell> getCellsOfStateShuffled(List<WatorCell> neighbors, int state) {
    List<WatorCell> ret = new ArrayList<>();
    for (WatorCell cell : neighbors) {
      if (cell.getCurrentState() == state) {
        ret.add(cell);
      }
    }
    Collections.shuffle(ret);
    return ret;
  }

  private void fillEmptyCell(WatorCell cell) {
    cell.updateStateEnergyAge(EMPTY, -1, -1);
  }

  private void reproduceFish(WatorCell currentCell, WatorCell nextCell){
    currentCell.updateStateEnergyAge(FISH, -1, 0);
    nextCell.updateStateEnergyAge(FISH, -1, 0);
  }
  private void increaseFishAge(WatorCell currentCell) {
    currentCell.updateStateEnergyAge(FISH, currentCell.getAge() + 1, -1);;
  }

  private void updateFish(WatorCell currentCell) {
    List<WatorCell> neighbors = getNeighbors(currentCell);
    List<WatorCell> emptyNeighbors = getCellsOfStateShuffled(neighbors, EMPTY);
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


  private void handleSharkMoveToEmptySpace(WatorCell currentCell, WatorCell nextCell) {
    if (nextCell.getNextState() == SHARK) {
      currentCell.updateStateEnergyAge(SHARK, currentCell.getEnergy() - 1,
          currentCell.getAge() + 1); //inc age of shark
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

  private void handleSharkEatFish(WatorCell currentCell, WatorCell nextCell) {
    if (nextCell.getNextState() == SHARK || nextCell.getNextState() == FISH) {
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

  private void handleSharkCantMove(WatorCell cell) {
    if (cell.getEnergy() > 1) {
      cell.updateStateEnergyAge(SHARK, cell.getEnergy() - 1, cell.getAge() + 1);
    } else {
      fillEmptyCell(cell);
    }
  }

  private void updateShark(WatorCell currentCell) {
    List<WatorCell> neighbors = getNeighbors(currentCell);
    List<WatorCell> emptyNeighbors = getCellsOfStateShuffled(neighbors, EMPTY);
    List<WatorCell> fishNeighbors = getCellsOfStateShuffled(neighbors, FISH);

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


  @Override
  public void transitionFunction() {
    for (int cellToUpdate : UPDATE_ORDER) {
      Iterator<WatorCell> gridIterator = getIterator();
      while (gridIterator.hasNext()) {
        WatorCell currentCell = gridIterator.next();
        if (currentCell.getNextState() == Cell.PLACEHOLDER &&
            currentCell.getCurrentState()==cellToUpdate) {
          switch (cellToUpdate) {
            case EMPTY: {
              currentCell.setNextState(EMPTY);
              break;
            }
            case FISH: {
              updateFish(currentCell);
              break;
            }
            case SHARK: {
              updateShark(currentCell);
              break;
            }
          }
        }
      }
    }
  }
}







