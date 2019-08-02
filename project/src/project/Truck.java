package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Pallets that has been picked, sequenced and checked at loading is loaded into
 * Truck.Truck is divided into the left and right side. Each side stores one
 * request, i.e. 2 Pallets.
 * 
 * <p>
 * It does not load any Pallet if the next picking request has not been
 * processed. Any Pallet good to load are going to wait for that request to
 * arrive first.
 * 
 * @author wansing, thioshar
 *
 */
public class Truck {

  ArrayList<ArrayList<Pallet>> left;
  ArrayList<ArrayList<Pallet>> right;
  ArrayList<ArrayList<Pallet>> awaitingNext;
  static int counter = 1; // change from 1 to 0 and it works for some reason
  Logger logger = Logger.getLogger("TheLogger"); // change it to private final
                                                 // static

  /**
   * Instantiates a new Truck.
   */
  public Truck() {
    left = new ArrayList<ArrayList<Pallet>>();
    right = new ArrayList<ArrayList<Pallet>>();
    awaitingNext = new ArrayList<ArrayList<Pallet>>();
  }

  /**
   * Get left pallet.
   * 
   * @return the list of pallets at left
   */
  public ArrayList<ArrayList<Pallet>> getLeft() {
    return left;
  }

  /**
   * Set left pallets.
   * 
   * @param left the pallet to set at left
   */
  public void setLeft(ArrayList<ArrayList<Pallet>> left) {
    this.left = left;
  }

  /**
   * Get right pallets.
   * 
   * @return the list of pallets at right
   */
  public ArrayList<ArrayList<Pallet>> getRight() {
    return right;
  }

  /**
   * Set right pallet.
   * 
   * @param right the pallet to set at right
   */
  public void setRight(ArrayList<ArrayList<Pallet>> right) {
    this.right = right;
  }

  /**
   * Get the waiting pallet.
   * 
   * @return the list of pallets that still waiting
   */
  public ArrayList<ArrayList<Pallet>> getAwaitingNext() {
    return awaitingNext;
  }

  /**
   * Set waiting pallet.
   * 
   * @param awaitingNext the pallet that is waiting
   */
  public void setAwaitingNext(ArrayList<ArrayList<Pallet>> awaitingNext) {
    this.awaitingNext = awaitingNext;
  }

  /**
   * Loads the pallets into the correct side of the truck. If the next request
   * is not processed, it will not be loaded and instead, will wait.
   * 
   * @param request the PickUpRequest
   * @param pallets the pallets to be loaded
   */
  public void load(PickUpRequest request, ArrayList<Pallet> pallets) {
    logger.finest("REQUEST LOAD ID " + request.getPickUpId());
    logger.finest("COUNTER LOAD " + counter);
    logger.info("AWAITINGNEXT: " + getAwaitingNext().isEmpty());
    if (!fullyLoaded()) {
      if (request.getPickUpId() == counter) {
        if (awaitingNext.isEmpty()) {
          if (counter % 2 == 0) {
            this.loadLeft(pallets);
          } else {
            this.loadRight(pallets);
          }
        } else {
          // if waiting list is not empty load the previous request first then
          // load
          // the current one
          if (counter % 2 == 0) {
            this.loadLeft(pallets);
            this.loadRight(awaitingNext.get(0));
          } else {
            this.loadRight(pallets);
            this.loadLeft(awaitingNext.get(0));
          }
        }
      } else {
        // If previous request has not been processed, wait for that one before
        // loading current one
        awaitingNext.add(pallets);
      }
    } else {
      logger.info("Truck is full");
    }
  }

  /**
   * Loads pallets to the left side of the truck.
   * 
   * @param pallets the pallets to be loaded
   */
  public void loadLeft(ArrayList<Pallet> pallets) {
    if (left.size() < 10) {
      left.add(pallets);
      counter++;
      this.recordOrder(pallets);
    }
  }

  /**
   * Loads pallets to the right side of the truck.
   * 
   * @param pallets the pallets to be loaded
   */
  public void loadRight(ArrayList<Pallet> pallets) {
    if (right.size() < 10) {
      right.add(pallets);
      counter++;
      this.recordOrder(pallets);
    }
  }

  /**
   * Checks to see if the truck is full.
   * 
   * @return whether the truck is full
   */
  public boolean fullyLoaded() {
    if (right.size() == 10 && left.size() == 10) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Records the order(the colour,model,skuF,skuB) added into an order.csv file
   * 
   * @param pallets the pallets to be loaded
   */
  public void recordOrder(ArrayList<Pallet> pallets) {
    String fileName2 = "translation.csv";
    File file2 = new File(fileName2);
    try {

      PrintWriter pw = new PrintWriter(
          new BufferedWriter(new FileWriter("order.csv", true)));
      Scanner inputstream = new Scanner(file2);
      inputstream.useDelimiter("\n");

      Pallet pallet = pallets.get(0);// front pallet
      for (String sku : pallet) {
        String skustring = sku.toString();
        while (inputstream.hasNext()) {
          String input = "";
          String line = inputstream.next();
          String[] data = line.split(",");
          if (skustring.equals(data[2])) {
            // write it in order.txt
            for (String d : data) {
              input += d;
              input += ",";
            }
            input = input.substring(0, input.length() - 1);
            pw.println(input);

            break;
          }
        }
      }
      inputstream.close();
      pw.close();
    } catch (FileNotFoundException exc) {
      // exc.printStackTrace();
      logger.severe("FileNotFoundException");
    } catch (IOException exc) {
      // exc.printStackTrace();
      logger.severe("IOException");
    }
  }
}
