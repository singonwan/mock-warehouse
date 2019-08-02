package project;

import checkers.LoadedChecker;
import checkers.PickedChecker;
import checkers.SequencedChecker;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import worker.Loader;
import worker.Picker;
import worker.Sequencer;
import worker.Worker;

/**
 * BarcodeReader does the scanning and checking of all the fascias being added
 * and also the pallets. It checks if the Picker, Sequencer and Loader has the
 * fascias in the same order as requested by the PickUpRequest they are working
 * on.
 * 
 * @author thioshar
 *
 */
public class BarcodeReader {
  Logger logger = Logger.getLogger("TheLogger");
  PickedChecker pickedC = new PickedChecker();
  LoadedChecker loadedC = new LoadedChecker();
  SequencedChecker sequencedC = new SequencedChecker();
  WarehouseFloor warehouse;
  WarehouseManager manager;
  Truck truck;

  /**
   * Instantiates the BarcodeReader and keeps track of which WarehouseFloor and
   * Truck it corresponds to.
   * 
   * @param warehouse The WarehouseFloor
   * @param truck The Truck
   * @param warehouseManager The WarehouseManager
   */
  public BarcodeReader(WarehouseFloor warehouse, Truck truck,
      WarehouseManager warehouseManager) {
    this.warehouse = warehouse;
    this.truck = truck;
    this.manager = warehouseManager;
  }

  /**
   * Scans the SKU of a fascia or the entire fascias collected depending if the
   * worker is a Picker, Sequencer or a Loader. Pickers will add one SKU and
   * check if they have the correct order when they picked all 8. Sequencer and
   * Loader will scan all the collected SKU and scan will check if they are all
   * in the correct order.
   * 
   * @param worker The worker doing the scanning
   * @return Whether the sequence of the SKU are correct
   */
  public boolean scan(Worker worker, String sku) {
    PickUpRequest request = worker.getRequest();
    List<String> skuList = (ArrayList<String>) request.getSkus();
    // PICKER
    if (worker instanceof Picker) {
      String location = warehouse.returnLocation(sku);
      logger.finest(worker.getName() + " got fascia at " + location);

      // Retrieve from the warehouse
      boolean replenishLocation = warehouse.picking(location);
      if (replenishLocation == true) {
        manager.addReplenishRequest(location);
      } else {
        logger.warning("The SKU number, " + sku + ", does not exist!");
      }
      // picker check the sku number
      if (pickedC.checkPicker(request, sku)) {
        worker.addFascia(sku);
        if (request.getSkusToPick().size() == 0) {
          logger.finest(worker.getName()
              + " has picked all required fascia for request ID "
              + request.getPickUpId());
          request.setPicked(true);
          worker.reset();
          return true;
        }
      } else {
        String correctSku = request.getSkusToPick().get(0);
        logger.info(worker.getName() + " is re-picking the correct fascia SKU: "
            + correctSku);
        scan(worker, correctSku);
      }
    }
    // SEQUENCER
    if (worker instanceof Sequencer && request.isPicked()) {
      if (sequencedC.firstCheck(request, skuList)) {
        sequencedC.check(request, skuList);
        return true;
      }
    }
    // LOADER
    if (worker instanceof Loader && request.isPicked()
        && request.isSequenced()) {
      loadedC.check(request, skuList, truck);
      return true;
    }
    return false;
  }

  /**
   * Rescan method that uses user input to decide and act on a rescan or not.
   * Only for Sequencers and Loaders.
   * 
   * @param worker The worker doing the scanning
   * @return Whether the sequence of the SKU are correct
   */
  public boolean rescan(Worker worker) {
    logger.info(worker.getName() + " requests a rescan.");
    return scan(worker, null);
  }

  /**
   * Reject method that is used when sequencer rejects a rescan. Will finish
   * sequencing or send back to PickUpRequests depending on the previous scan
   * results.
   * 
   * @param worker The worker doing the scanning
   */
  public boolean rejectRescanSequencer(Worker worker) {
    PickUpRequest request = worker.getRequest();
    logger.info(worker.getName() + " rejects rescan.");
    if (request.isSequenced()) {
      request.addPallet(sequencedC.getFrontPallet());
      request.addPallet(sequencedC.getBackPallet());
      sequencedC.reset();
      return true;
    } else {
      request.setPicked(false);
      manager.addPickUpRequest(request);
      worker.reset();
      return false;
    }
  }

  /**
   * Reject method that is used when loader rejects a rescan. Will finish
   * loading or send back to PickUpRequests depending on the previous scan
   * results.
   * 
   * @param worker The worker doing the scanning
   */
  public boolean rejectRescanLoader(Worker worker) {
    PickUpRequest request = worker.getRequest();
    logger.info(worker.getName() + " rejects rescan.");
    if (request.isLoaded()) {
      truck.load(request, request.getPallet());
      return true;
    } else {
      request.setPicked(false);
      if (request.isSequenced() == true) {
        request.setSequenced(false);
        sequencedC.reset();
      }
      manager.addPickUpRequest(request);
      worker.reset();
      return false;
    }
  }

  /**
   * Simulation for a sequencer to scan incorrectly.
   * 
   * @param worker the sequencer sequencing
   * @return false since it is sequenced wrong
   */
  public boolean scanIncorrectly(Worker worker) {
    PickUpRequest request = worker.getRequest();
    if (worker instanceof Sequencer && request.isPicked()) {
      logger.warning("STOP! Sequenced order incorrect!");
    }
    return false;
  }

}
