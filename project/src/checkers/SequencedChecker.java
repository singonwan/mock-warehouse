package checkers;

import java.util.List;
import project.PickUpRequest;

/**
 * SequencedChecker inherits from Checker and is solely responsible for the
 * checking done by the Sequencer.
 * 
 * @author thioshar
 *
 */
public class SequencedChecker extends Checker {

  private int counter = 0;

  /**
   * Sequences the fascia picked into the correct pallets.
   * 
   * @param skuList The 8 fascias required by the PickUpRequest
   */
  public void sequence(List<String> skuList) {
    for (int i = 0; i < 8; i++) {
      String sku = skuList.get(i);
      if (counter % 2 == 0) {
        frontPallet.addFascia(sku);
        counter++;
      } else if (counter % 2 != 0) {
        backPallet.addFascia(sku);
        counter++;
      }
    }
  }

  /**
   * Check to see if the fascia sequenced matches the fascia sequence requested
   * by the PickUpRequest.
   * 
   * @param request The current PickUpRequest
   * @param skuList The 8 fascias required by the PickUpRequest
   * @return Whether The sequencing was done correctly
   */
  public boolean check(PickUpRequest request, List<String> skuList) {
    sequence(skuList);
    logger.finest("Scanning and checking the pallet");
    boolean checking = super.check(skuList);
    if (checking == false) {
      logger.warning("STOP! Sequenced order incorrect!");
    } else {
      logger.info("Sequencing order is correct");
      request.setSequenced(true);
    }
    return checking;
  }

  /**
   * A method to check before scanning to see if all the correct SKUs have been
   * picked.
   * 
   * @param request The current PickUpRequest
   * @param skuList The 8 fascias required by the PickUpRequest
   * @return Whether The picking was done correctly
   */
  public boolean firstCheck(PickUpRequest request, List<String> skuList) {
    for (String sku : skuList) {
      if (request.getPickedSkus().contains(sku) == false) {
        return false;
      }
    }
    return true;
  }
}
