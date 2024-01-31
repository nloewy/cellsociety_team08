package cellsociety.Model;

public class WatorCell extends Cell{

  private int myEnergy;
  private int myTime;
  /**
   * Constructs a cell object
   *
   * @param initialState is the original state of the cell, either randomly set or determined from a
   *                     configuration file
   * @param x            is the x-coordinate of the cell on the 2-dimensional grid
   * @param y            is the y-coordinate of the cell on the 2-dimensional grid
   */
  public WatorCell(int initialState, int x, int y, int energy, int time) {
    super(initialState, x, y);
    myEnergy = energy;
    myTime = time;
  }

  public int getEnergy(){
    return myEnergy;
  }
  public int getTime(){
    return myTime;
  }
  public void setEnergy(int energy){
    myEnergy = energy;
  }
  public void setTime(int time){
    myTime = time;
  }


}
