package project;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * WarehouseSystem instantiates the WarehouseReader, WarehouseManager and
 * FaxMachine.
 * 
 * @author thioshar
 *
 */
public class WarehouseSystem {

  /**
   * The main method to run the code.
   */
  public static void main(String[] args) { 
    String path = Paths.get("").toAbsolutePath().toString();
    final Logger logger = Logger.getLogger("TheLogger");
    ConsoleHandler ch = new ConsoleHandler();
    ch.setLevel(Level.ALL);
    FileHandler handler = null;
    try {
      handler = new FileHandler(path + "/project" + ".log", true);
      handler.setLevel(Level.ALL);
      
    } catch (Exception exc) {
      exc.printStackTrace();
    }
    handler.setFormatter(new SimpleFormatter());
    logger.addHandler(handler);
    logger.addHandler(ch);
    logger.setLevel(Level.ALL);
    
    WarehouseSystem system = new WarehouseSystem();
    
    String fileName = system.read(args[0]);
    String traversal = system.read(args[1]);
    String translation = system.read(args[2]);
    String initialcsv = "initial.csv";
    WarehouseReader warehouseReader = new WarehouseReader(traversal);
    WarehouseFloor warehouseFloor = warehouseReader.csvReader(initialcsv);
    WarehouseManager warehouseManager = new WarehouseManager(warehouseFloor, translation);
    FaxMachine faxMachine = new FaxMachine(warehouseManager);
    faxMachine.readOrders(fileName);
    ArrayList<String> kv = warehouseFloor.returnKeyValue();
    warehouseReader.finalCsvWriter(kv);
    handler.close();
  }

  /**
   * Takes a String that could be a relative path and gets the file name.
   */
  public String read(String arg) {
    String[] argArray = arg.split("/");
    String fileName = argArray[argArray.length - 1];
    
    return fileName;
  }
  
}
