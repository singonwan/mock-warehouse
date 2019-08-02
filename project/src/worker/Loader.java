package worker;

import java.util.ArrayList;
import project.Pallet;

/**
 * The Loader class. A type of worker.
 * 
 * <p>Loaders look at the picking request id, and, using the barcode reader, scan
 * the SKUs of the fascia to be loaded to make sure that the orders are loaded
 * in the correct order.
 * 
 * @author rosswest, thioshar
 * 
 */

public class Loader extends Worker {
  
  private ArrayList<Pallet> pallet = new ArrayList<Pallet>();

  /**
   * Create a new Loader with the name name.
   * 
   * @param name The name of the loader
   */
  public Loader(String name) {
    super(name);
  }

  /**
   * Add a pallet the sequencer is sequencing to the pallets .
   * 
   * @param pal A pallet
   */
  public void addPallet(Pallet pal) {
    pallet.add(pal);
  }
}
