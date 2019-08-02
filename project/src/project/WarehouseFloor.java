package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

/**
 * WarehouseFloor is the storage room. It stores of all the fascias in their
 * appropriate pick face.
 * 
 * @author wansing
 *
 */
public class WarehouseFloor {

  private HashMap<String, Integer> floor;
  Logger logger = Logger.getLogger("TheLogger");
  private String traversal;

  /**
   * Instantiates the WarehouseFloor. Every pick face will start with a full
   * inventory.
   * 
   * @param fileName name of the file to read the initial inventory from.
   */
  public WarehouseFloor(String fileName) {
    traversal = fileName;
    // String fileName = "traversal_table.csv";
    File file = new File(fileName);
    floor = new HashMap<>();

    try {
      Scanner inputstream = new Scanner(file);
      inputstream.useDelimiter("\n");
      while (inputstream.hasNext()) {
        String line = inputstream.next();
        String[] data = line.split(",");
        String location = data[0] + data[1] + data[2] + data[3];
        this.floor.put(location, 30);
      }
    } catch (FileNotFoundException exc) {
      // exc.printStackTrace();
      logger.severe("FileNotFoundException");
    }

  }

  /**
   * Sets the floor layout, and how many fascias are in each location.
   * 
   * @param floor a HashMap<String, Integer> where the strings are locations and
   *        the values are how many fascias are in each location.
   */
  public void setFloor(HashMap<String, Integer> floor) {
    this.floor = floor;
  }


  /**
   * Reduced the inventory for the key when it is picked. When there is less
   * than 5 left, a replenishing request is created.
   * 
   * @param key the location of the pick face
   * @return true if the picker picks, and false if the location doesn't exist
   */
  public boolean picking(String key) {
    if (floor.containsKey(key)){
      
    int newVal = floor.get(key) - 1;
    floor.replace(key, newVal);
    if (floor.get(key) == 5) {
      replenishRequest(key);
    }
    return true;
    }
    return false;
  }

  /**
   * Notify the need of a replenish request.
   * 
   * @param key the location of the pick face
   */
  // called when only 5 left
  public String replenishRequest(String key) {
    logger.info("Need to replenish: " + key);
    return key;
  }

  /**
   * Replenish the items at specified key.
   * 
   * @param key the location of the pick face
   */
  public void replenish(String key) {
    floor.replace(key, 30);
  }

  /**
   * Replaces the current entry with the new key and value.
   * 
   * @param key new key
   * @param value new value
   */
  public void replace(String key, int value) {
    floor.replace(key, value);
  }

  /**
   * Returns the inventory number for the floor.
   * 
   * @return inventory number
   */
  public ArrayList<String> returnKeyValue() {
    ArrayList<String> output = new ArrayList<>();
    Set<String> keys = floor.keySet();
    for (String k : keys) {
      int value = floor.get(k);
      String result = k + value;
      output.add(result);
    }
    return output;
  }

  /**
   * Returns the pick face location of an SKU.
   * 
   * @param sku the fascia's SKU number
   * @return pick face location
   */
  public String returnLocation(String sku) {
    File file = new File(traversal);

    try {
      @SuppressWarnings("resource")
      Scanner inputstream = new Scanner(file);
      inputstream.useDelimiter("\n");
      while (inputstream.hasNext()) {
        String line = inputstream.next();
        String[] data = line.split(",");
        String skuNum = data[4];
        if (sku.equals(skuNum)) {
          String location = data[0] + data[1] + data[2] + data[3];
          return location;
        }
      }
    } catch (FileNotFoundException exc) {
      // exc.printStackTrace();
      logger.severe("FileNotFoundException");
    }
    return null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;
    WarehouseFloor other = (WarehouseFloor) obj;
    if (floor == null) {
      if (other.floor != null)
        return false;
    }
    if (this == obj)
      return true;

    if (!floor.equals(other.floor))
      return false;
    return true;
  }

  public int getAmount(String location) {
    return floor.get(location);
  }
}
