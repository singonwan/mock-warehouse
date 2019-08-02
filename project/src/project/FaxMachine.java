package project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * FaxMachine takes in a file of orders and parses through it, performing the
 * appropriate action for each individual order.
 * 
 * @author kimest13
 *
 */
public class FaxMachine {

  Logger logger = Logger.getLogger("TheLogger");

  private WarehouseManager warehouseManager;
  private List<List<String>> orders = new ArrayList<List<String>>(4);

  /**
   * Instantiates a FaxMachine that has a corresponding WarehouseManager.
   * 
   * @param warehouseManager The WarehouseManager
   */
  public FaxMachine(WarehouseManager warehouseManager) {
    this.warehouseManager = warehouseManager;
  }

  /**
   * Return the list of orders FaxMachine currently contains.
   * 
   * @return the list of orders FaxMachine currently contains
   */
  public List<List<String>> getOrders() {
    return orders;
  }

  /**
   * Add an order to the current order list, orders. If there are 4 orders, send
   * them to the WarehouseManager to create a PickUpRequest for them.
   * 
   * @param model the model of the car from the order in the fax
   * @param colour the colour of the car from the order in the fax
   */
  public void addOrder(String model, String colour) {
    List<String> order = new ArrayList<String>();
    order.add(model);
    order.add(colour);
    orders.add(order);
    if (orders.size() == 4) {
      warehouseManager.makePickUpRequest(orders);
      orders.clear();
    }
  }

  /**
   * Reads the order from a text file and perform the appropriate action for
   * each individual order.
   * 
   * @param file a text file with the fax of the order
   */
  public void readOrders(String file) {
    BufferedReader br = null;
    // Parsing through the file
    try {
      br = new BufferedReader(new FileReader(file));
      String curLine = "";
      String lineSplit = " ";

      while ((curLine = br.readLine()) != null) {
        // Input line
        logger
            .finest(" =============== INPUT: " + curLine + " =============== ");

        String[] line = curLine.split(lineSplit);
        // Performing appropriate action based on the order
        if (line[0].equals("Order")) {
          logger.info("Order added: " + line[1] + " " + line[2]);
          this.addOrder(line[1], line[2]);

        } else if (line[0].equals("Picker")) {
          if (line[2].equals("pick")) {
            warehouseManager.pickerAction(line[1], line[2], line[3]);
          } else {
            warehouseManager.pickerAction(line[1], line[2], null);
          }
        } else if (line[0].equals("Loader")) {
          warehouseManager.loaderAction(line[1], line[2]);
        } else if (line[0].equals("Sequencer")) {
          warehouseManager.sequencerAction(line[1], line[2], line[3]);
        } else if (line[0].equals("Replenisher")) {
          String location = "";
          for (int i = 3; i < 7; i++) {
            location += line[i];
          }
          warehouseManager.replenisherAction(line[1], location);
        }
      }
    } catch (FileNotFoundException exc) {
      exc.printStackTrace();
    } catch (IOException exc) {
      exc.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException exc) {
          exc.printStackTrace();
        }
      }
    }
  }
}
