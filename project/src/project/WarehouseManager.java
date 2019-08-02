package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import worker.Loader;
import worker.Picker;
import worker.Replenisher;
import worker.Sequencer;
import worker.Worker;

/**
 * A WarehouseManager for a warehouse. It creates PickUpRequests and is in
 * charge of queues of these requests based on which stage the PickUpRequest is
 * at. They are also in charge of keeping track of the workers.
 * 
 * @author thioshar, kimest13
 *
 */
public class WarehouseManager {

  Logger logger = Logger.getLogger("TheLogger");

  private Queue<PickUpRequest> pickUpRequests = new LinkedList<PickUpRequest>();
  private Queue<PickUpRequest> sequenceRequests =
      new LinkedList<PickUpRequest>();
  private Queue<PickUpRequest> loadRequests = new LinkedList<PickUpRequest>();
  private Queue<String> replenishRequests = new LinkedList<String>();

  private ArrayList<Worker> pickers = new ArrayList<>();
  private ArrayList<Worker> sequencers = new ArrayList<>();
  private ArrayList<Worker> loaders = new ArrayList<>();
  private ArrayList<Worker> replenishers = new ArrayList<>();

  private HashMap<String, ArrayList<Worker>> workers;
  private WarehouseFloor warehouse;
  private WarehousePicking optimizer = new WarehousePicking();
  private BarcodeReader barcodeReader;
  private Truck truck;
  private String translationFile;

  /**
   * Instantiate the WarehouseManager with a corresponding WarehouseFloor.
   * 
   * @param warehouseFloor the WarehouseFloor
   * @param translation the name of the translation file for SKUs
   */
  public WarehouseManager(WarehouseFloor warehouseFloor, String translation) {
    this.warehouse = warehouseFloor;
    this.truck = new Truck();
    this.translationFile = translation;
    barcodeReader = new BarcodeReader(warehouse, truck, this);
    workers = new HashMap<String, ArrayList<Worker>>();
    workers.put("Picker", pickers);
    workers.put("Sequencer", sequencers);
    workers.put("Loader", loaders);
    workers.put("Replenisher", replenishers);
  }

  /**
   * Return the hashMap of workers for this warehouse.
   * 
   * @return the hashMap of workers
   */
  public HashMap<String, ArrayList<Worker>> getWorkers() {
    return workers;
  }

  /**
   * Creates a PickUpRequest with 4 orders.
   * 
   * @param orders list of 4 orders which contain information of the model and
   *        colour of the fascia
   */
  public void makePickUpRequest(List<List<String>> orders) {
    PickUpRequest newPickUpRequest =
        new PickUpRequest(orders, this.translationFile);
    ArrayList<String> location = optimizer.optimize(newPickUpRequest);
    newPickUpRequest.setLocations(location);
    addPickUpRequest(newPickUpRequest);
  }

  /**
   * Add a PickUpRequest to the Queue of PickUpRequests.
   * 
   * @param request a PickUpRequest to add to the Queue
   */
  public void addPickUpRequest(PickUpRequest request) {
    pickUpRequests.add(request);
  }

  /**
   * Returns the first PickUpRequest in the Queue and removes it from the Queue.
   * 
   * @return the first PickUpRequest in the Queue
   */
  public PickUpRequest returnPickUpRequest() {
    return pickUpRequests.remove();
  }

  /**
   * Performs the action a Picker has to perform.
   * 
   * @param name name of the picker
   * @param action the action the picker has to perform
   */
  public void pickerAction(String name, String action, String sku) {
    boolean exist = checkWorker("Picker", name);
    // Create the Picker if they don't exist
    if (exist == false) {
      createWorker("Picker", name);
      logger.finest(name + " was added as a worker");
    }
    if (workers.containsKey("Picker")) {
      for (Worker worker : workers.get("Picker")) {
        if (worker.getName().equals(name)) {
          // Gives the Picker a PickUpRequest they are solely responsible for
          if (action.equals("ready")) {
            worker.assignRequest(pickUpRequests.remove());
            worker.changeReadyStatus(false);
          }
          // Pick a fascia requested by the PickUpRequest
          if (action.equals("pick")) {
            logger.finest(name + " pick " + sku);
            barcodeReader.scan(worker, sku);
            if (worker.getRequest().isPicked()) {
              logger.info("Move request " + worker.getRequest().getPickUpId()
                  + " to sequence requests");
              sequenceRequests.add(worker.getRequest());
            }
          }
          // "to" indicates the "to Marshaling" to
          if (action.equals("to")) {
            worker.changeReadyStatus(true);
          }
        }
      }
    }
  }

  /**
   * Gives Sequencer a request ready for sequencing and gets the Sequencer to
   * sequence and scan all the fascias received.
   * 
   * @param name of the sequencer
   */
  public void sequencerAction(String name, String action, String mode) {
    boolean exist = checkWorker("Sequencer", name);
    // Create the Sequencer if they don't exist
    if (exist == false) {
      logger.finest(name + " was made into worker");
      createWorker("Sequencer", name);
    }
    if (workers.containsKey("Sequencer")) {
      for (Worker worker : workers.get("Sequencer")) {
        if (worker.getName().equals(name)) {
          if (action.equals("ready")) {
            worker.changeReadyStatus(true);
            // Assign and scans
          } else if (action.equals("sequences") && worker.isReady()) {
            if (sequenceRequests.isEmpty() == false) {
              // If they sequences it correctly
              worker.assignRequest(sequenceRequests.remove());
              worker.changeReadyStatus(false);
              if (mode.equals("true")) {
                barcodeReader.scan(worker, null);
                logger.info("Scan complete");
              }
              // If they sequences it incorrectly
              else {
                barcodeReader.scanIncorrectly(worker);
              }
            } else {
              logger.info("No request to sequence");
            }
          } else if (action.equals("rescans")) {
            barcodeReader.rescan(worker);
            logger.info("Rescan complete");
          } else if (action.equals("requests")) {
            logger.info(worker.getName() + " requests rescan");
          } else if (action.equals("rejects")) {
            boolean reject = barcodeReader.rejectRescanSequencer(worker);
            if (reject == true) {
              logger.info("Move request " + worker.getRequest().getPickUpId()
                  + " to loading requests");
              loadRequests.add(worker.getRequest());
            } else {
              logger.warning("Sequencing error, sent back to PickUpRequest");
            }
          }
          worker.reset();
        }
      }
    }
  }

  /**
   * Gives Loader a request ready for loading and gets the Loader scan the
   * pallets received and load if in correct order.
   * 
   * @param name name of the Loader
   */
  public void loaderAction(String name, String action) {
    boolean exist = checkWorker("Loader", name);
    // Create the Loader if they don't exist
    if (exist == false) {
      createWorker("Loader", name);
    }
    if (workers.containsKey("Loader")) {
      for (Worker worker : workers.get("Loader")) {

        if (worker.getName().equals(name)) {
          if (action.equals("ready")) {
            worker.changeReadyStatus(true);
          } else if (action.equals("loads") && worker.isReady()) {
            if (loadRequests.isEmpty() == false) {
              worker.changeReadyStatus(false);
              // Assign and scans
              worker.assignRequest(loadRequests.remove());
              barcodeReader.scan(worker, null);
              logger.info("Scan complete.");
            } else {
              logger.warning("No requests to load");
            }
          } else if (action.equals("rescans")) {
            barcodeReader.rescan(worker);
            logger.info("Rescan complete.");
          } else if (action.equals("requests")) {
            logger.info(worker.getName() + " requests rescan");
          } else if (action.equals("rejects")) {
            boolean reject = barcodeReader.rejectRescanLoader(worker);
            if (reject == true) {
              logger.info(worker.getName() + " finished loading");
            } else {
              logger.warning("Loading error, sent back to PickUpRequest");
            }
          }
          worker.reset();
        }
      }
    }
  }

  /**
   * Check to see if the worker exist.
   * 
   * @param type the type of the worker
   * @param name the name of the worker
   * @return whether worker exist
   */
  public boolean checkWorker(String type, String name) {
    boolean hasIt = false;
    if (workers.containsKey(type)) {
      for (Worker worker : workers.get(type)) {
        if (worker.getName().equals(name)) {
          hasIt = true;
        }
      }
    }
    return hasIt;
  }

  /**
   * Creates and adds a worker to the HashMap of Workers.
   * 
   * @param type the type of worker to instantiate
   * @param name the name of the worker to instantiate
   */
  public void createWorker(String type, String name) {
    if (type == "Picker") {
      Picker picker = new Picker(name);
      workers.get("Picker").add(picker);
    } else if (type == "Sequencer") {
      Sequencer sequencer = new Sequencer(name);
      workers.get("Sequencer").add(sequencer);
    } else if (type == "Loader") {
      Loader loader = new Loader(name);
      workers.get("Loader").add(loader);
    } else if (type == "Replenisher") {
      Replenisher replenisher = new Replenisher(name);
      workers.get("Replenisher").add(replenisher);
    }
  }

  /**
   * Gets Replenisher to replenish the fascias at the given location if there is
   * no replenishing request.
   * 
   * @param name name of Replenisher
   * @param location location to be replenished
   */
  public void replenisherAction(String name, String location) {
    boolean exist = checkWorker("Replenisher", name);
    // Create the Replenisher if they don't exist
    if (exist == false) {
      createWorker("Replenisher", name);
    }
    if (workers.containsKey("Replenisher")) {
      for (Worker worker : workers.get("Replenisher")) {
        if (worker.getName().equals(name)) {
          if (replenishRequests.isEmpty() == false) {
            warehouse.replenish(replenishRequests.remove());
            logger.info(
                "Replenisher " + worker.getName() + " replenished " + location);
          } else {
            warehouse.replenish(location);
            logger.info(
                "Replenisher " + worker.getName() + " replenished " + location);
          }
        }
      }
    }
  }

  /**
   * Sends a ReplenishRequest to specified location.
   * 
   * @param location the location of the shelf needed to replenish
   */
  public void addReplenishRequest(String location) {
    replenishRequests.add(location);
  }
}
