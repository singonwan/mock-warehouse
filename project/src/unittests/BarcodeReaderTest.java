package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.BarcodeReader;
import project.Pallet;
import project.PickUpRequest;
import project.Truck;
import project.WarehouseFloor;
import project.WarehouseManager;
import worker.Loader;
import worker.Picker;
import worker.Sequencer;
import worker.Worker;

public class BarcodeReaderTest {
  private String translation = "translation.csv";
  WarehouseFloor warehouse = new WarehouseFloor("traversal_table.csv");
  WarehouseManager manager = new WarehouseManager(warehouse, translation);
  Truck truck = new Truck();
  Worker w1 = new Picker("Alice");
  Worker w2 = new Sequencer("Bob");
  Worker w3 = new Loader("Charlie");
  BarcodeReader r = new BarcodeReader(warehouse, truck, manager);
  
  @Before
  public void setUp() throws Exception {
    warehouse = new WarehouseFloor("traversal_table.csv");
    manager = new WarehouseManager(warehouse, translation);
    truck = new Truck();
    r = new BarcodeReader(warehouse, truck, manager);
  }

  @After
  public void tearDown() throws Exception {
    
  }

  @Test
  public void testScanForPickerNoRepick() {
    //Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    
    w1.assignRequest(request);
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertTrue(r.scan(w1, "6"));
  }
  @Test
  public void testScanForSequencerTrue() {
    //Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    //picker picks all 8
    w1.assignRequest(request);
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertTrue(r.scan(w1, "6"));
    assertTrue(request.isPicked());
    w2.assignRequest(request);
    assertTrue(r.scan(w2, null));
  }
  @Test
  public void testScanForLoaderTrue() {
    //Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    //picker picks all 8
    w1.assignRequest(request);
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertTrue(r.scan(w1, "6"));
    assertTrue(request.isPicked());
    w2.assignRequest(request);
    assertTrue(r.scan(w2, null));
    w3.assignRequest(request);
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    List<String> skuList = new ArrayList<String>();
    for (Integer i = 1; i < 9; i++) {
      frontPallet.addFascia("5");
      skuList.add("5");
      i++;
    }
    for (Integer i = 2; i < 9; i++) {
      backPallet.addFascia("6");
      skuList.add("6");
      i++;
    }
    request.addPallet(frontPallet);
    request.addPallet(backPallet);
    assertTrue(r.scan(w3, null));
  }
  @Test
  public void testRescanForPicker() {
    //Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    
    w1.assignRequest(request);
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertTrue(r.scan(w1, "6"));
  }
  @Test
  public void testScanForPickerWrongSKU() {
    //Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SEL");
    order.add("Silver");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    w1.assignRequest(request);
    r.scan(w1, "95");
    r.scan(w1, "96");
    r.scan(w1, "95");
    r.scan(w1, "96");
    r.scan(w1, "95");
    r.scan(w1, "96");
    r.scan(w1, "95");
    assertTrue(r.scan(w1, "96"));
  }
  @Test
  public void testScanForSequencerFalse() {
    //Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    //picker picks all 8
    w1.assignRequest(request);
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertFalse(r.scan(w1, "6"));
    assertFalse(r.scan(w1, "5"));
    assertTrue(r.scan(w1, "6"));
    assertTrue(request.isPicked());
    w2.assignRequest(request);
    r.scanIncorrectly(w2);
    assertFalse(r.rejectRescanSequencer(w2));
  }
}
