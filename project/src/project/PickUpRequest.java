package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A PickUpRequest for a set of 4 orders. It has a unique ID. Keeps track of
 * whether it has been picked, sequenced and loaded.
 * 
 * @author kimest13, thioshar
 *
 */
public class PickUpRequest {

  Logger logger = Logger.getLogger("TheLogger");
  private List<String> skus = new ArrayList<>();
  private List<String> skusToPick = new ArrayList<>();
  private List<String> pickedSkus = new ArrayList<>();
  private List<String> locations = new ArrayList<>();
  private ArrayList<Pallet> pallet = new ArrayList<Pallet>(2);
  private int pickUpId;
  private int skuCounter = 0;
  private static int idCounter = 1;
  private boolean picked = false;
  private boolean sequenced = false;
  private boolean loaded = false;
  private String fileName;


  /**
   * Constructs a new PickUpRequest by finding the sku's of the orders and
   * creating a unique ID for each new PickUpRequest.
   * 
   * @param orders list of 4 orders which contain information of the model and
   *        colour of the fascia
   */
  public PickUpRequest(List<List<String>> orders, String file) {
    fileName = file;
    skus = findSku(orders);
    if (skus == null) {
      logger.warning("PickUpRequest did not contain 4 proper orders.");
      logger.warning("PickUpRequest cannot proceed.");
    }
    skusToPick = findSku(orders);
    setPickUpId();
    logger.info("PickUpRequest " + this.getPickUpId() + " was made");
  }

  /**
   * A getter for list of SKUs.
   * 
   * @return the list of SKU
   */
  public List<String> getSkus() {
    return skus;
  }

  /**
   * A getter for the unique pickUpId.
   * 
   * @return the pickUpId
   */
  public int getPickUpId() {
    return pickUpId;
  }

  /**
   * Creates the unique pickUpId.
   */
  private void setPickUpId() {
    pickUpId = idCounter;
    idCounter++;
  }

  /**
   * NOTE: This is a testing function. Do not use it unless you are a unittest.
   *
   * Sets the ID Counter.
   * 
   * @param id number to set the id counter to
   */
  public static void setIdCounter(int id) {
    idCounter = id;
  }

  /**
   * Finds each SKU for each fascia in the orders. Referenced
   * https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/ for
   * reading csv.
   * 
   * @param orders list of 4 orders which contain information of the model and
   *        colour of the fascia
   * @return a list of all the SKU values of the fascia
   */
  public List<String> findSku(List<List<String>> orders) {
    // if the orders received are invalid
    if (orders.size() != 4) {
      logger.warning("PickUpRequest needs 4 orders to proceed.");
      return null;
    }

    List<String> sku = new ArrayList<>();

    // for every order, look through the translation file to find respective sku
    for (List<String> order : orders) {
      File file = new File(fileName);
      try {
        String curLine = "";
        String csvSplit = ",";
        @SuppressWarnings("resource")
        Scanner inStream = new Scanner(file);

        // inputs the fascia of each order (front and back) into sku
        while (inStream.hasNext()) {
          curLine = inStream.next();
          int check = 0;
          int count = 0;
          String[] row = curLine.split(csvSplit);
          for (String col : row) {
            if (order.get(0).equals(col)) {
              check++;
              continue;
            } else if (order.get(1).equals(col)) {
              check++;
              continue;
            } else if ((check == 2) && (count < 3)) {
              String skuVal = col;
              sku.add(skuVal);
              count++;
              if (count > 2) {
                break;
              }
            } else {
              break;
            }
          }
        }
      } catch (FileNotFoundException exc) {
        exc.printStackTrace();
      }
    }
    return sku;
  }

  /**
   * Gets the collected pallets for this request.
   * 
   * @return the front and back pallet collected
   */
  public ArrayList<Pallet> getPallet() {
    return pallet;
  }

  /**
   * Adds the pallet to the collected pallets.
   * 
   * @param pallet the Pallet
   */
  public void addPallet(Pallet pallet) {
    this.pallet.add(pallet);
  }

  /**
   * Gets the number of fascias that has been successfully collected.
   * 
   * @return number of fascias collected
   */
  public int getCounter() {
    int count = skuCounter;
    if (skuCounter == 7) {
      skuCounter = 0;
    }
    skuCounter++;
    return count;
  }

  /**
   * Return whether is has been picked.
   * 
   * @return whether is has been picked.
   */
  public boolean isPicked() {
    return picked;
  }

  /**
   * Sets the status to the given status.
   * 
   * @param status the status of if item has been picked
   */
  public void setPicked(boolean status) {
    this.picked = status;
  }

  /**
   * Return whether is has been sequenced.
   * 
   * @return whether is has been sequenced.
   */
  public boolean isSequenced() {
    return sequenced;
  }

  /**
   * Sets the status to the given status.
   * 
   * @param status the status of if item has been sequenced
   */
  public void setSequenced(boolean status) {
    this.sequenced = status;
  }

  /**
   * Return whether is has been loaded.
   * 
   * @return whether is has been loaded.
   */
  public boolean isLoaded() {
    return loaded;
  }

  /**
   * Sets the status to the given status.
   * 
   * @param status the status of if item has been loaded
   */
  public void setLoaded(boolean status) {
    this.loaded = status;
  }

  /**
   * Gets the list of picked SKU's.
   * 
   * @return the list of picked SKU's
   */
  public List<String> getPickedSkus() {
    return pickedSkus;
  }

  /**
   * Adds an SKU the list of picked SKU's.
   * 
   * @param pickedSKU: The fascia's SKU, as a string.
   */
  public void addPickedSkus(String pickedSku) {
    this.pickedSkus.add(pickedSku);
  }

  /**
   * Gets the list of SKU's to pick.
   * 
   * @return the list of SKU's to pick.
   */
  public List<String> getSkusToPick() {
    return skusToPick;
  }

  /**
   * Sets the list of locations of the SKU's in the pickup request.
   * 
   * @param locations the list of locations
   */
  public void setLocations(List<String> locations) {
    this.locations = locations;
  }

  /**
   * Gets the list of locations of the SKU's in the pickup request.
   * 
   * @param locations the list of locations
   * @return
   */
  public List<String> getLocations() {
    return locations;
  }
}
