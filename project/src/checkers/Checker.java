package checkers;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import project.Pallet;
import project.PickUpRequest;

/**
 * Checker checks the fascias scanned by the Picker, Sequencer and Loader to
 * ensure that it was scanned and arranged in the same order requested by the
 * PickUpRequest.
 * 
 * @author thioshar
 *
 */
public class Checker {
  Logger logger = Logger.getLogger("TheLogger");
  Pallet frontPallet = new Pallet();
  Pallet backPallet = new Pallet();

  /**
   * Check to see if the fascia the Picker picked matches the fascias asked for
   * by the PickUpRequest.
   * 
   * @param arrayList The 8 fascias the picker had picked
   * @param skuList The 8 fascias required by the PickUpRequest
   * @return Whether The picking was done correctly
   */
  public boolean checkPicker(PickUpRequest request, String sku) {
    List<String> skus = request.getSkusToPick();
    if (skus.contains(sku)) {
      skus.remove(sku);
      request.addPickedSkus(sku);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check to see if the fascia sequenced or to be loaded matches the fascia
   * sequence requested by the PickUpRequest.
   * 
   * @param skuList The 8 fascias required by the PickUpRequest
   * @return Whether The sequencing or loading was done correctly
   */
  public boolean check(List<String> skuList) {
    Iterator<String> theSku = skuList.iterator();
    Iterator<String> checkFront = frontPallet.getPallet().iterator();
    Iterator<String> checkBack = backPallet.getPallet().iterator();
    int counter = 0;
    while (theSku.hasNext()) {
      if (counter % 2 == 0) {
        if (checkFront.next() != theSku.next()) {
          return false;
        }
        counter++;
      }
      if (counter % 2 != 0) {
        if (checkBack.next() != theSku.next()) {
          return false;
        }
      }
      counter++;
    }
    return true;
  }

  /**
   * Resets both the frontPallet and the backPallet.
   */
  public void reset() {
    frontPallet = new Pallet();
    backPallet = new Pallet();
  }

  /**
   * Returns the pallet with the front fascias.
   * 
   * @return pallets
   */
  public Pallet getFrontPallet() {
    return frontPallet;
  }

  /**
   * Set the front pallet to a new Pallet.
   * 
   * @param frontPallet the new pallet
   */
  public void setFrontPallet(Pallet frontPallet) {
    this.frontPallet = frontPallet;
  }

  /**
   * Returns the pallet with the back fascias.
   * 
   * @return pallets
   */
  public Pallet getBackPallet() {
    return backPallet;
  }

  /**
   * Set the back pallet to a new Pallet.
   * 
   * @param backPallet the new pallet
   */
  public void setBackPallet(Pallet backPallet) {
    this.backPallet = backPallet;
  }
}
