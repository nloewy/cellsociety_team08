package cellsociety.model.simulation;

public class Records {

  public record GameOfLifeRecord(int aliveToAliveMin, int deadToAliveMax, int aliveToAliveMax,
                                 int deadToAliveMin, String gridType, String cellShape){}

  public record PercolationRecord(int percolatedNeighbors, String gridType, String cellShape){}

  public record FireRecord(int neighborsToIgnite, double probTreeIgnites, double probTreeCreated,
                           String gridType, String cellShape){}

  public record SchellingRecord( double proportionNeededToStay, String gridType, String cellShape){}

  public record WatorRecord(int fishAgeOfReproduction, int sharkAgeOfReproduction, int initialEnergy,
                            int energyBoost, String gridType, String cellShape){}
}
