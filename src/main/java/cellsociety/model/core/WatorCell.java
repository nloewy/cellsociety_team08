package cellsociety.model.core;

public class WatorCell extends Cell {

  private int myCurrentAge;
  private int myCurrentEnergy;
  private int myNextAge;
  private int myNextEnergy;
  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   */

  public WatorCell(int initialState, int x, int y, int energy, int age) {
    super(initialState, x, y);
    myCurrentEnergy = energy;
    myCurrentAge = age;
    myNextEnergy = -1;
    myNextAge = -1;
  }
  @Override
  public void updateStates(){
   super.updateStates();
   myCurrentAge = myNextAge;
   myCurrentEnergy = myNextEnergy;
   myNextAge = PLACEHOLDER;
   myNextEnergy = PLACEHOLDER;
  }

  public void updateStateEnergyAge(int state, int energy, int age) {
    setNextState(state);
    setNextEnergy(energy);
    setNextAge(age);
  }
  public int getEnergy() {
    return myCurrentEnergy;
  }

  public int getAge() {
    return myCurrentAge;
  }

  public void setNextEnergy(int energy) {
    myNextEnergy = energy;
  }

  public void setNextAge(int time) {
    myNextAge = time;
  }


}
