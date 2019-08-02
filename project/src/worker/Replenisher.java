package worker;

import worker.Worker;

/**
 * The Replenisher class. A type of worker.
 * 
 * <p>Whenever a picker records that a fascia has been picked, the system
 * checks whether that kind of fascia is running out. If there are, the
 * system triggers a request for repleninshing.
 * 
 * <p>This is the worker that carries that task out.
 * 
 * @author rosswest, thioshar
 * 
 */

public class Replenisher extends Worker {
  /**
   * Create a new Replenisher with the name name.
   * 
   * @param name The name of the replenisher
   */
  public Replenisher(String name) {
    super(name);
  }
}
