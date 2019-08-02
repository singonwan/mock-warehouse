package unittests;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import project.FaxMachine;
import project.WarehouseFloor;
import project.WarehouseManager;
import worker.Worker;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FaxMachineTest {
  // count for number of workers made to keep track of their id
  private String translation = "translation.csv";
  private String traversal = "traversal_table.csv";
  private WarehouseFloor warehouseFloor = new WarehouseFloor(traversal);
  private WarehouseManager warehouseManager = new WarehouseManager(warehouseFloor, translation);
  private FaxMachine faxMachine = new FaxMachine(warehouseManager);

  @Before
  public void setUp() throws Exception {
    String traversal = "traversal_table.csv";
    warehouseFloor = new WarehouseFloor(traversal);
    warehouseManager = new WarehouseManager(warehouseFloor, translation);
    faxMachine = new FaxMachine(warehouseManager);
  }

  @After
  public void tearDown() throws Exception {
    warehouseFloor = null;
    warehouseManager = null;
    faxMachine = null;
    Worker.setWorkerId(0);
  }
  
  @Test
  public void testGetOrders() {
    List<List<String>> testOrders = new ArrayList<List<String>>(4);
    List<String> order1 = new ArrayList<>();
    order1.add("SES");
    order1.add("White");
    testOrders.add(order1);
    String colour = "White";
    String model = "SES";
    faxMachine.addOrder(model, colour);
    assertEquals(testOrders, faxMachine.getOrders());
  }
  
  @Test
  public void testAddOneOrder() {
    List<List<String>> testOrders = new ArrayList<List<String>>(4);
    List<String> order1 = new ArrayList<>();
    order1.add("SES");
    order1.add("White");
    testOrders.add(order1);
    String colour = "White";
    String model = "SES";
    faxMachine.addOrder(model, colour);
    assertEquals(testOrders, faxMachine.getOrders());
  }
  
  @Test
  public void testAddThreeOrders() {
    List<List<String>> testOrders = new ArrayList<List<String>>(4);
    List<String> order1 = new ArrayList<>();
    order1.add("SES");
    order1.add("White");
    testOrders.add(order1);
    List<String> order2 = new ArrayList<>();
    order2.add("S");
    order2.add("Red");
    testOrders.add(order2);
    List<String> order3 = new ArrayList<>();
    order3.add("SES");
    order3.add("White");
    testOrders.add(order3);
    String colour1 = "White";
    String model1 = "SES";
    String colour2 = "Red";
    String model2 = "S";
    faxMachine.addOrder(model1, colour1);
    faxMachine.addOrder(model2, colour2);
    faxMachine.addOrder(model1, colour1);
    assertEquals(testOrders, faxMachine.getOrders());
  }

  @Test
  /**
   * Check FaxMachine empties orders list once four orders have been received.
   */
  public void testAddFourOrders() {
    List<List<String>> testOrders = new ArrayList<List<String>>(4);
    String colour1 = "White";
    String model1 = "SES";
    String colour2 = "Red";
    String model2 = "S";
    faxMachine.addOrder(model1, colour1);
    faxMachine.addOrder(model2, colour2);
    faxMachine.addOrder(model1, colour1);
    faxMachine.addOrder(model2, colour2);
    assertEquals(testOrders, faxMachine.getOrders());
  }
  
  @Test
  /**
   * Check if Order instructions work in method readOrder.
   */
  public void testReadOrderOrder() throws IOException {
    String content = "Order SES White\nOrder S Beige\n";
    String path = null;
    FileWriter fw = null;
    File file = null;

    path = new java.io.File(".").getCanonicalPath() + File.separator + "testReadOrder.txt";

    file = new File(path);
    if (!file.exists()) {
      file.createNewFile();
    }
    System.out.println("GOT HERE");

    fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(content);
    System.out.println("WROTE TO FILE");
    bw.close();

    List<List<String>> testOrders = new ArrayList<List<String>>(4);
    List<String> order1 = new ArrayList<>();
    order1.add("SES");
    order1.add("White");
    testOrders.add(order1);
    List<String> order2 = new ArrayList<>();
    order2.add("S");
    order2.add("Beige");
    testOrders.add(order2);
    faxMachine.readOrders("testReadOrder.txt");
    assertEquals(testOrders, faxMachine.getOrders());
    file.delete();
  }
  
  @Test
  /**
   * Check if Picker instructions work in method readOrder by checking if worker created.
   */
  public void testReadOrderPicker() throws IOException {
    String content = "Order SES Blue\nOrder SES Red\nOrder SE Grey\nOrder SE Grey\nPicker Alice ready\n";
    String path = null;
    FileWriter fw = null;
    File file = null;

    path = new java.io.File(".").getCanonicalPath() + File.separator + "testReadPicker.txt";

    file = new File(path);
    if (file.exists() == false) {
      file.createNewFile();
    }
    System.out.println("GOT HERE");

    fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(content);
    System.out.println("WROTE TO FILE");
    bw.close();

    String name = "Alice";
    // the number of workers that were made in this test,
    // should also be the workerID of the worker that is being tested
    int workerCount = 1;
    faxMachine.readOrders("testReadPicker.txt");
    System.out.println("got here");
    System.out.println("ALICE ID " + warehouseManager.getWorkers().get("Picker").get(0).getWorkerId()); //TODO
    
    assertEquals(name, warehouseManager.getWorkers().get("Picker").get(0).getName());
    assertEquals(workerCount, warehouseManager.getWorkers().get("Picker").get(0).getWorkerId());
    file.delete();
  }
  
  @Test
  /**
   * Check if Sequencer instructions work in method readOrder by checking if worker created.
   */
  public void testReadOrderSequencer() throws IOException {
    String content = "Order SES White\nOrder S White\nOrder S White\nOrder SES White\nPicker Alice ready\n"
        + "Picker Alice pick 5\nPicker Alice pick 6\nPicker Alice pick 1\nPicker Alice pick 2\n"
        + "Picker Alice pick 1\nPicker Alice pick 2\nPicker Alice pick 5\nPicker Alice pick 6\n"
        + "Sequencer Sue sequences true\n";
    String path = null;
    FileWriter fw = null;
    File file = null;

    path = new java.io.File(".").getCanonicalPath() + File.separator + "testReadSequencer.txt";

    file = new File(path);
    if (!file.exists()) {
      file.createNewFile();
    }
    System.out.println("GOT HERE");

    fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(content);
    System.out.println("WROTE TO FILE");
    bw.close();

    String name = "Sue";
    // the number of workers that were made in this test,
    // should also be the workerID of the worker that is being tested
    int workerCount = 2;
    
    faxMachine.readOrders("testReadSequencer.txt");
    System.out.println("got here");
    System.out.println("SUE ID " + warehouseManager.getWorkers().get("Sequencer").get(0).getWorkerId()); //TODO
    
    assertEquals(name, warehouseManager.getWorkers().get("Sequencer").get(0).getName());
    assertEquals(workerCount, warehouseManager.getWorkers().get("Sequencer").get(0).getWorkerId());
    file.delete();
  }
  
  @Test
  /**
   * Check if Loader instructions work in method readOrder by checking if worker created.
   */
  public void testReadOrderLoader() throws IOException {
    String content = "Order SES White\nOrder S White\nOrder S White\nOrder SES White\nPicker Alice ready\n"
        + "Picker Alice pick 5\nPicker Alice pick 6\nPicker Alice pick 1\nPicker Alice pick 2\n"
        + "Picker Alice pick 1\nPicker Alice pick 2\nPicker Alice pick 5\nPicker Alice pick 6\n"
        + "Picker Alice to Marshaling\nSequencer Sue sequences true\nSequencer Sue rejects rescan\n"
        + "Loader Bill loads\nLoader Bill rejects";
    String path = null;
    FileWriter fw = null;
    File file = null;

    path = new java.io.File(".").getCanonicalPath() + File.separator + "testReadLoader.txt";

    file = new File(path);
    if (!file.exists()) {
      file.createNewFile();
    }
    System.out.println("GOT HERE");

    fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(content);
    System.out.println("WROTE TO FILE");
    bw.close();

    String name = "Bill";
    // the number of workers that were made in this test,
    // should also be the workerID of the worker that is being tested
    int workerCount = 3;
    
    faxMachine.readOrders("testReadLoader.txt");
    System.out.println("got here");
    System.out.println("BILL ID " + warehouseManager.getWorkers().get("Loader").get(0).getWorkerId()); //TODO
    
    assertEquals(name, warehouseManager.getWorkers().get("Loader").get(0).getName());
    assertEquals(workerCount, warehouseManager.getWorkers().get("Loader").get(0).getWorkerId());
    file.delete();
  }
  
  @Test
  /**
   * Check if Loader instructions work in method readOrder by checking if worker created.
   */
  public void testReadOrderReplenisher() throws IOException {
    String content = "Order SES White\nOrder S White\nOrder S White\nOrder SES White\nPicker Alice ready\n"
        + "Picker Alice pick 5\nPicker Alice pick 6\nPicker Alice pick 1\nPicker Alice pick 2\n"
        + "Picker Alice pick 1\nPicker Alice pick 2\nPicker Alice pick 5\nPicker Alice pick 6\n"
        + "Sequencer Sue sequences true\nSequencer Sue rejects rescan\nLoader Bill loads\nLoader Bill rejects\n"
        + "Replenisher Ruby replenish A 1 1 2\n";
    String path = null;
    FileWriter fw = null;
    File file = null;

    path = new java.io.File(".").getCanonicalPath() + File.separator + "testReadReplenisher.txt";

    file = new File(path);
    if (!file.exists()) {
      file.createNewFile();
    }
    System.out.println("GOT HERE");

    fw = new FileWriter(file.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);

    bw.write(content);
    System.out.println("WROTE TO FILE");
    bw.close();
    
    String name = "Ruby";
    // the number of workers that were made in this test,
    // should also be the workerID of the worker that is being tested
    int workerCount = 4;
    
    faxMachine.readOrders("testReadReplenisher.txt");
    System.out.println("got here");
    System.out.println("RUBY ID " + warehouseManager.getWorkers().get("Replenisher").get(0).getWorkerId()); //TODO
    
    assertEquals(name, warehouseManager.getWorkers().get("Replenisher").get(0).getName());
    assertEquals(workerCount, warehouseManager.getWorkers().get("Replenisher").get(0).getWorkerId());
    file.delete();
  }
  
  @Test
  public void testFileNotFoundException() {
    faxMachine.readOrders("blah");
  }
  
//  @Test(expected = IOException.class)
//  public void testIOException() throws IOException {
//    faxMachine.readOrders("");
//  }
  
}
