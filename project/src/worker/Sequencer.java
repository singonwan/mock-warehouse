package worker;

import java.util.ArrayList;
import project.Pallet;

/**
 * The sequencer class. A type of worker.
 * 
 * <p>Sequencers sequence the fascia onto the pallets, then visually
 * inspect that they have the fascia in the right places. They then
 * record the SKU's of the fascia using a barcode reader.
 * 
 * @author rosswest, thioshar
 * 
 */

public class Sequencer extends Worker {
  
  private ArrayList<Pallet> pallets = new ArrayList<Pallet>();

  /**
   * Create a new Sequencer with the name name.
   * 
   * @param name The name of the sequencer
   */
  public Sequencer(String name) {
    super(name);
  }

  /**
   * Add a pallet the sequencer is sequencing to the pallets .
   * 
   * @param pal A pallet
   */
  public void addPallet(Pallet pal) {
    pallets.add(pal);
  }

}
