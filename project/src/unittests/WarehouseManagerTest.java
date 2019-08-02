package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.PickUpRequest;
import project.WarehouseFloor;
import project.WarehouseManager;
import worker.Loader;
import worker.Picker;
import worker.Replenisher;
import worker.Sequencer;
import worker.Worker;

public class WarehouseManagerTest {
  
  private String translation = "translation.csv";
  private String traversal = "traversal_table.csv";
  private WarehouseFloor warehouseFloor = new WarehouseFloor(traversal);// = new WarehouseFloor();
  private WarehouseManager warehouseManager = new WarehouseManager(warehouseFloor, translation);

  @Before
  public void setUp() throws Exception {
    warehouseManager = new WarehouseManager(warehouseFloor, translation);
  }

  @After
  public void tearDown() throws Exception {
    Worker.setWorkerId(0);
  }

  @Test
  public void testGetWorkers() {
    HashMap<String, ArrayList<Worker>> workers = new HashMap<>();
    ArrayList<Worker> pickers = new ArrayList<>();
    ArrayList<Worker> loaders = new ArrayList<>();
    ArrayList<Worker> sequencers = new ArrayList<>();
    ArrayList<Worker> replenishers = new ArrayList<>();
    workers.put("Picker", pickers);
    workers.put("Loader", loaders);
    workers.put("Sequencer", sequencers);
    workers.put("Replenisher", replenishers);
    Picker picker = new Picker("Alice");
    Loader loader = new Loader("Bob");
    Sequencer sequencer = new Sequencer("Rob");
    Replenisher replenisher = new Replenisher("Sam");
    workers.get("Picker").add(picker);
    workers.get("Loader").add(loader);
    workers.get("Sequencer").add(sequencer);
    workers.get("Replenisher").add(replenisher);
    warehouseManager.createWorker("Picker", "Alice");
    warehouseManager.createWorker("Loader", "Bob");
    warehouseManager.createWorker("Sequencer", "Rob");
    warehouseManager.createWorker("Replenisher", "Sam");
    ArrayList<String> expected = new ArrayList<>();
    expected.add(workers.get("Picker").get(0).getName());
    expected.add(workers.get("Loader").get(0).getName());
    expected.add(workers.get("Sequencer").get(0).getName());
    expected.add(workers.get("Replenisher").get(0).getName());
    ArrayList<String> got = new ArrayList<>();
    got.add(warehouseManager.getWorkers().get("Picker").get(0).getName());
    got.add(warehouseManager.getWorkers().get("Loader").get(0).getName());
    got.add(warehouseManager.getWorkers().get("Sequencer").get(0).getName());
    got.add(warehouseManager.getWorkers().get("Replenisher").get(0).getName());
    assertEquals(expected, got);
//    assertEquals(workers, warehouseManager.getWorkers());
  }
  
  @Test
  public void testMakePickUpRequest() {
    List<List<String>> orders = new ArrayList<>();
    ArrayList<String> order1 = new ArrayList<>();
    order1.add("White");
    order1.add("SE");
    orders.add(order1);
    ArrayList<String> order2 = new ArrayList<>();
    order2.add("Red");
    order2.add("S");
    orders.add(order2);
    ArrayList<String> order3 = new ArrayList<>();
    order3.add("Blue");
    order3.add("SEL");
    orders.add(order3);
    ArrayList<String> order4 = new ArrayList<>();
    order4.add("Beige");
    order4.add("S");
    orders.add(order4);
    PickUpRequest expected = new PickUpRequest(orders, translation);
    warehouseManager.makePickUpRequest(orders);
    warehouseManager.pickerAction("Alice", "ready", null);
    assertEquals(expected.getSkus(), 
        warehouseManager.getWorkers().get("Picker").get(0).getRequest().getSkus());
  }
  
  // TODO can't test addPickUpRequest
  @Test
  public void testReturnPickUpRequest() {
    List<List<String>> orders = new ArrayList<>();
    ArrayList<String> order1 = new ArrayList<>();
    order1.add("White");
    order1.add("SE");
    orders.add(order1);
    ArrayList<String> order2 = new ArrayList<>();
    order2.add("Red");
    order2.add("S");
    orders.add(order2);
    ArrayList<String> order3 = new ArrayList<>();
    order3.add("Blue");
    order3.add("SEL");
    orders.add(order3);
    ArrayList<String> order4 = new ArrayList<>();
    order4.add("Beige");
    order4.add("S");
    orders.add(order4);
    PickUpRequest expected = new PickUpRequest(orders, translation);
    warehouseManager.makePickUpRequest(orders);
    assertEquals(expected.getSkus(), warehouseManager.returnPickUpRequest().getSkus());
  }
  
  // TODO skipped pickerAction
  @Test
  public void testSequencerAction() {
    List<List<String>> orders = new ArrayList<>();
    ArrayList<String> order1 = new ArrayList<>();
    order1.add("White");
    order1.add("SE");
    orders.add(order1);
    ArrayList<String> order2 = new ArrayList<>();
    order2.add("Red");
    order2.add("S");
    orders.add(order2);
    ArrayList<String> order3 = new ArrayList<>();
    order3.add("Blue");
    order3.add("SEL");
    orders.add(order3);
    ArrayList<String> order4 = new ArrayList<>();
    order4.add("Beige");
    order4.add("S");
    orders.add(order4);
    PickUpRequest pickUpRequest = new PickUpRequest(orders, translation);
    warehouseManager.makePickUpRequest(orders);
    warehouseManager.pickerAction("Alice", "ready", null);
    warehouseManager.pickerAction("Alice", "pick", "1");
    warehouseManager.pickerAction("Alice", "pick", "2");
    warehouseManager.pickerAction("Alice", "pick", "3");
    warehouseManager.pickerAction("Alice", "pick", "4");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "7");
    warehouseManager.pickerAction("Alice", "pick", "8");
    warehouseManager.pickerAction("Alice", "to", null);
    warehouseManager.sequencerAction("Rob", "ready", null);
    warehouseManager.sequencerAction("Rob", "sequences", "true");
    assertEquals(pickUpRequest.getSkus(), 
        warehouseManager.getWorkers().get("Sequencer").get(0).getRequest().getSkus());
  }
  
  @Test
  public void testLoaderAction() {
    List<List<String>> orders = new ArrayList<>();
    ArrayList<String> order1 = new ArrayList<>();
    order1.add("White");
    order1.add("SE");
    orders.add(order1);
    ArrayList<String> order2 = new ArrayList<>();
    order2.add("Red");
    order2.add("S");
    orders.add(order2);
    ArrayList<String> order3 = new ArrayList<>();
    order3.add("Blue");
    order3.add("SEL");
    orders.add(order3);
    ArrayList<String> order4 = new ArrayList<>();
    order4.add("Beige");
    order4.add("S");
    orders.add(order4);
    PickUpRequest pickUpRequest = new PickUpRequest(orders, translation);
    warehouseManager.makePickUpRequest(orders);
    warehouseManager.pickerAction("Alice", "ready", null);
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "to", null);
    warehouseManager.sequencerAction("Rob", "ready", null);
    warehouseManager.sequencerAction("Rob", "sequences", "true");
    warehouseManager.sequencerAction("Rob", "rejects", null);
    warehouseManager.loaderAction("Bob", "ready");
    warehouseManager.loaderAction("Bob", "loads");
    warehouseManager.loaderAction("Bob", "rejects");
    assertEquals(pickUpRequest.getSkus(), 
        warehouseManager.getWorkers().get("Loader").get(0).getRequest().getSkus());
  }
  
  @Test
  public void testReplenisherAction() {
    List<List<String>> orders = new ArrayList<>();
    ArrayList<String> order1 = new ArrayList<>();
    order1.add("Red");
    order1.add("SEL");
    orders.add(order1);
    ArrayList<String> order2 = new ArrayList<>();
    order2.add("Red");
    order2.add("S");
    orders.add(order2);
    ArrayList<String> order3 = new ArrayList<>();
    order3.add("Blue");
    order3.add("SEL");
    orders.add(order3);
    ArrayList<String> order4 = new ArrayList<>();
    order4.add("Beige");
    order4.add("S");
    orders.add(order4);
    warehouseManager.makePickUpRequest(orders);
    warehouseManager.pickerAction("Alice", "ready", null);
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "to", null);
    warehouseManager.sequencerAction("Rob", "ready", null);
    warehouseManager.sequencerAction("Rob", "sequences", "true");
    warehouseManager.sequencerAction("Rob", "rejects", null);
    warehouseManager.loaderAction("Bob", "ready");
    warehouseManager.loaderAction("Bob", "loads");
    int before = 29;
    int after = 29;
    int replenish = warehouseFloor.getAmount("A122");
    warehouseManager.replenisherAction("Jim", "A122");
    int replenished = warehouseFloor.getAmount("A122");
    String expected = before + " " + after;
    String got = replenish + " " + replenished;
    assertEquals(expected , got);
  }
  @Test
  public void testReplenisherActionLocation() {
    int before = 30;
    int after = 30;
    int replenish = warehouseFloor.getAmount("B123");
    warehouseManager.replenisherAction("Jim", "B123");
    int replenished = warehouseFloor.getAmount("B123");
    String expected = before + " " + after;
    String got = replenish + " " + replenished;
    assertEquals(expected , got);
  }
  
  // TODO how to make barcodeR == false?
  @Test
  public void testLoaderActionFailLoad() {
    List<List<String>> orders = new ArrayList<>();
    ArrayList<String> order1 = new ArrayList<>();
    order1.add("White");
    order1.add("SE");
    orders.add(order1);
    ArrayList<String> order2 = new ArrayList<>();
    order2.add("Red");
    order2.add("S");
    orders.add(order2);
    ArrayList<String> order3 = new ArrayList<>();
    order3.add("Blue");
    order3.add("SEL");
    orders.add(order3);
    ArrayList<String> order4 = new ArrayList<>();
    order4.add("Beige");
    order4.add("S");
    orders.add(order4);
    PickUpRequest pickUpRequest = new PickUpRequest(orders, translation);
    warehouseManager.makePickUpRequest(orders);
    warehouseManager.pickerAction("Alice", "ready", null);
    warehouseManager.pickerAction("Alice", "pick", "1");
    warehouseManager.pickerAction("Alice", "pick", "2");
    warehouseManager.pickerAction("Alice", "pick", "3");
    warehouseManager.pickerAction("Alice", "pick", "4");
    warehouseManager.pickerAction("Alice", "pick", "5");
    warehouseManager.pickerAction("Alice", "pick", "6");
    warehouseManager.pickerAction("Alice", "pick", "7");
    warehouseManager.pickerAction("Alice", "pick", "8");
    warehouseManager.pickerAction("Alice", "to", null);
    warehouseManager.sequencerAction("Rob", "ready", null);
    warehouseManager.sequencerAction("Rob", "sequences", "true");
    warehouseManager.sequencerAction("Rob", "rejects", null);
    warehouseManager.loaderAction("Bob", "ready");
    warehouseManager.loaderAction("Bob", "loads");
    assertEquals(pickUpRequest.getSkus(), 
        warehouseManager.getWorkers().get("Loader").get(0).getRequest().getSkus());
  }
  
  @Test 
  public void testCheckWorkerTrue() {
    warehouseManager.createWorker("Picker", "Alice");
    warehouseManager.createWorker("Sequencer", "Rob");
    warehouseManager.createWorker("Loader", "Bob");
    warehouseManager.createWorker("Picker", "Pam");
    boolean expected = true;
    boolean got = warehouseManager.checkWorker("Picker", "Pam");
    assertEquals(expected, got);
  }
  
  @Test 
  public void testCheckWorkerFalse() {
    warehouseManager.createWorker("Picker", "Alice");
    warehouseManager.createWorker("Sequencer", "Rob");
    warehouseManager.createWorker("Loader", "Bob");
    warehouseManager.createWorker("Picker", "Pam");
    boolean expected = false;
    boolean got = warehouseManager.checkWorker("Picker", "Jam");
    assertEquals(expected, got);
  }
  
  @Test 
  public void testCreateWorker() {
    warehouseManager.createWorker("Picker", "Alice");
    int workerId = warehouseManager.getWorkers().get("Picker").get(0).getWorkerId();
    String workerName = warehouseManager.getWorkers().get("Picker").get(0).getName();
    String expected = "Alice 1";
    String got = workerName + " " + workerId;
    assertEquals(expected, got);
  }
  
}
