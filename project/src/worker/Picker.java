package worker;

/**
 * The Picker class. A type of worker.
 * 
 * <p>Pickers pick fascia of various colours from pallets, and then take them to the
 * marshalling area for sequencing.
 * 
 * @author rosswest thioshar
 * 
 */
public class Picker extends Worker {
  
  /**
   * Create a new Picker with the name name.
   * 
   * @param name The name of the picker
   */
  public Picker(String name) {
    super(name);
  }
}
