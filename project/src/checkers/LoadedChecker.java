package checkers;

import java.util.List;
import java.util.logging.Logger;

import project.PickUpRequest;
import project.Truck;

/**
 * LoadedChecker inherits from Checker and is solely responsible for the
 * checking done by the Loader.
 * 
 * @author thioshar
 *
 */
public class LoadedChecker extends Checker {
  Logger logger = Logger.getLogger("TheLogger");

  /**
   * Check to see if the fascia to be loaded matches the fascia sequence
   * requested by the PickUpRequest.
   * 
   * @param request The current PickUpRequest
   * @param skuList The 8 fascias required by the PickUpRequest
   * @param truck The truck where the pallets will be loaded into
   * @return Whether The loading of the fascias was done correctly
   */
  public boolean check(PickUpRequest request, List<String> skuList,
      Truck truck) {
    logger.finest("Scanning and checking the pallet");
    frontPallet = request.getPallet().get(0);
    backPallet = request.getPallet().get(1);
    boolean checking = super.check(skuList);
    if (checking == false) {
      logger.warning("STOP! Loaded in the wrong sequence");
    } else {
      logger.info("Loading sequence correct");
      request.setLoaded(true);
    }
    return checking;
  }

}
