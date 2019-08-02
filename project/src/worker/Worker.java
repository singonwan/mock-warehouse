package worker;

import java.util.ArrayList;
import java.util.logging.Logger;

import project.PickUpRequest;

/**
 * The abstract class worker. Not to be instantiated.
 * 
 * <p>It contains the worker's name, whether or not he/she is ready to work, and
 * their assigned barcodeReader.
 * 
 * @author rosswest, thioshar
 * 
 */
public abstract class Worker {
  Logger logger = Logger.getLogger("TheLogger");
  private String name;
  private PickUpRequest request;
  private static int workerID;
  // The workerID is a counter that increments every single time that a worker
  // is instantiated.
  // After trying to write test code, I understand why we're supposed to avoid
  // the word 'static'
  // Each time a worker is instantiated, it needs a new ID, thus workerID is
  // static.
  // protected instead of private because every inheriting worker class also uses this
  protected boolean isReady = true; 
  private ArrayList<String> pickedFascias = new ArrayList<String>();

  /**
   * Constructs a new worker via assigning it a name.
   * 
   * @param name The name of the worker.
   */
  public Worker(String name) {
    this.name = name;
    this.isReady = true;
    workerID++;
  }

  /**
   * Adds a SKU number to the pickedFascia.
   * 
   * @param sku The fascia SKU number to be added
   */
  public void addFascia(String sku) {
    pickedFascias.add(sku);
  }

  /**
   * Return the fascias collected by the worker.
   * 
   * @return The fascias collected
   */
  public ArrayList<String> getFascias() {
    return pickedFascias;
  }

  /**
   * Gets name.
   * 
   * @return The worker's name, as a String.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Gets worker ID.
   * 
   * @return The worker's ID, as an int.
   */
  public int getWorkerId() {
    return workerID;
  }
  
  /**
   * Sets worker ID. For testing purposes.
   */
  public static void setWorkerId(int change) {
    workerID = change;
  }

  /**
   * Returns whether or not the worker is ready to be assigned another task.
   * 
   * @return The worker's status as ready, as a boolean.
   */
  public boolean isReady() {
    return this.isReady;
  }

  /**
   * Changes whether or not the worker is ready to be assigned another task.
   * 
   * @param ready Boolean: The worker's status as ready.
   */
  public void changeReadyStatus(boolean ready) {
    this.isReady = ready;
    if (ready == false) {
      logger.finest(this.getName() + " is now busy");
    } else {
      logger.finest(this.getName() + " is ready");
    }
  }

  /**
   * Return the PickUpRequest the worker is working on.
   * 
   * @return The PickUpRequest
   */
  public PickUpRequest getRequest() {
    return this.request;
  }

  /**
   * Assign the PickUpRequest to the worker.
   * 
   * @param request The PickUpRequest
   */
  public void assignRequest(PickUpRequest request) {
    this.request = request;
    logger.info(
        this.getName() + " assigned request ID: " + request.getPickUpId());
  }

  /**
   * Resets the fascias the worker has picked. Done when they receive a new
   * PickUpRequest.
   */
  public void reset() {
    pickedFascias = new ArrayList<String>();
    this.changeReadyStatus(true);;
  }
}
