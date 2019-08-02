package project;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Pallet holds a maximum of 4 fascias. It implements Iterable to iterate
 * through the fascias it stores.
 * 
 * @author thioshar
 *
 */
public class Pallet implements Iterable<String> {

  final int max = 4;
  private ArrayList<String> pallet = new ArrayList<String>(max);


  /**
   * Adds the fascia's SKU number (which is a string) to the pallet.
   * 
   * @param sku Fascia's SKU number, as a string
   */
  public void addFascia(String sku) {
    if (pallet.size() < max) {
      pallet.add(sku);
    }
  }

  /**
   * Returns the size of the pallet.
   * 
   * @return size of pallet
   */
  public int size() {
    return pallet.size();
  }

  /**
   * Return the pallet of fascias it has collected.
   * 
   * @return the pallet
   */
  public ArrayList<String> getPallet() {
    return pallet;
  }

  @Override
  /**
   * Return the iterator for the pallet.
   */
  public Iterator<String> iterator() {
    return new PalletIterator();
  }

  /**
   * PalletIterator is the iterator for a Pallet.
   * 
   * @author wansing
   *
   */
  class PalletIterator implements Iterator<String> {

    private int index = 0;

    @Override
    /**
     * Return whether the iterator has a next SKU number.
     * 
     * @return whether iterator has a next SKU number
     */
    public boolean hasNext() {
      return index < pallet.size();

    }

    @Override
    /**
     * Return the next SKU number.
     * 
     * @return the SKU number.
     */
    public String next() {
      return pallet.get(index++);
    }

  }
}
