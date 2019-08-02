package checkers;

import java.util.logging.Logger;
import project.PickUpRequest;

/**
 * PickedChecker inherits from Checker and is solely responsible for the
 * checking done by the Picker.
 * 
 * @author thioshar
 *
 */
public class PickedChecker extends Checker {
  Logger logger = Logger.getLogger("TheLogger");

  /**
   * Check to see if the fascia the Picker picked matches the fascias asked for
   * by the PickUpRequest.
   * 
   * @param request that is being worked on
   * @param sku The SKU that has been picked
   * @return Whether The picking was done correctly
   */
  public boolean checkPicker(PickUpRequest request, String sku) {
    logger.finest("Checking now...");
    boolean checking = super.checkPicker(request, sku);
    if (checking == false) {
      logger.warning("STOP! Picked the wrong fascia");
      return false;
    } else {
      logger.info("Picked correct fascia");
      return true;
    }
  }
}
