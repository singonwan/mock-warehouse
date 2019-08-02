package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * WarhouseReader is in charge of recording and taking note of the inventory in
 * the WareHouseFloor. It provides the final state of the WarehouseFloor when
 * the system is done.
 * 
 * @author wansing
 *
 */
public class WarehouseReader {
  /**
   * Instantiates the WarehoouseFloor with the appropriate amount of inventory
   * in each pick face.
   * 
   * @return the WarehoouseFloor
   */
  // TODO change it to private final static
  Logger logger = Logger.getLogger("TheLogger"); 
                                                 
  private String traversal;
  
  /**
   * Instantiates a new Truck.
   * @param traversal is the traversal table.
   */
  public WarehouseReader(String traversal) {
    this.traversal = traversal;
  }

  /**
   * Reads in a .csv file and updates the WarehouseFloor.
   * Reads the initial.csv file
   * 
   * @param fileName the .csv file that is to be read.
   * @return The updated WarehouseFloor
   */
  public WarehouseFloor csvReader(String fileName) {

    File file = new File(fileName);

    WarehouseFloor whf = new WarehouseFloor(traversal);

    try {

      Scanner inputstream = new Scanner(file);
      inputstream.useDelimiter("\n");
      while (inputstream.hasNext()) {
        String line = inputstream.next();
        String[] am = line.split(",");
        int amount = Integer.parseInt(am[4]);
        String info = line.replace(",", "");
        info = info.substring(0, info.length() - 2);
        whf.replace(info, amount);
      }
      inputstream.close();
    } catch (FileNotFoundException exc) {
      logger.severe("FileNotFoundException");
    }
    return whf;

  }

  /**
   * Writes the final state of the WarehouseFloor into a csv file.
   * 
   * @param kv the key and values from the WarehouseFloor an ArrayList 
   *     of keyvalue string ie A10030
   */
  public void finalCsvWriter(ArrayList<String> kv) {
    try {
      PrintWriter pw = new PrintWriter("final.csv", "UTF-8");
      for (String k : kv) {
        String str = "";
        if (k.length() >= 6){
          String lastTwo = k.substring(k.length() - 2, k.length());
          for (int i = 0; i < k.length() - 2; i++) {
            str += k.charAt(i) + ",";
          }
          str += lastTwo;
        } else{
          String lastOne = k.substring(k.length() -1, k.length());
          for (int i = 0; i < k.length() - 1; i++) {
            str += k.charAt(i) + ",";
          }
          str += lastOne;
        }

        pw.println(str);
      }
      pw.close();
    } catch (FileNotFoundException exc) {
      logger.severe("FileNotFoundException");
    } catch (UnsupportedEncodingException exc) {
      logger.severe("UnsupportedEncodingException");
    }
  }
  
}


