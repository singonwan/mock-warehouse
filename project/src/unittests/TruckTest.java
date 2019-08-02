package unittests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import project.Pallet;
import project.PickUpRequest;
import project.Truck;

public class TruckTest {

  @Test
  public void testTruck() {
    Truck truck = new Truck();
  }

  @Test
  public void testGetLeft() {
    Truck truck = new Truck();
    ArrayList<ArrayList<Pallet>> array = new ArrayList<ArrayList<Pallet>>();
    assertEquals(array, truck.getLeft());
  }

  @Test
  public void testSetLeft() {
    Truck truck = new Truck();
    ArrayList<ArrayList<Pallet>> array = new ArrayList<ArrayList<Pallet>>();
    truck.setLeft(array);
    assertEquals(array, truck.getLeft());
  }

  @Test
  public void testGetRight() {
    Truck truck = new Truck();
    ArrayList<ArrayList<Pallet>> array = new ArrayList<ArrayList<Pallet>>();
    assertEquals(array, truck.getRight());
  }

  @Test
  public void testSetRight() {
    Truck truck = new Truck();
    ArrayList<ArrayList<Pallet>> array = new ArrayList<ArrayList<Pallet>>();
    truck.setRight(array);
    assertEquals(array, truck.getRight());
  }

  @Test
  public void testGetAwaitingNext() {
    Truck truck = new Truck();
    ArrayList<ArrayList<Pallet>> array = new ArrayList<ArrayList<Pallet>>();
    assertEquals(array, truck.getAwaitingNext());
  }

  @Test
  public void testSetAwaitingNext() {
    Truck truck = new Truck();
    ArrayList<ArrayList<Pallet>> array = new ArrayList<ArrayList<Pallet>>();
    truck.setAwaitingNext(array);
    assertEquals(array, truck.getAwaitingNext());
  }

  @Test
  public void testLoad() {
    String translation = "translation.csv";
    // Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest.setIdCounter(0);
    PickUpRequest request1 = new PickUpRequest(orders, translation);
    PickUpRequest request2 = new PickUpRequest(orders, translation);
    PickUpRequest request3 = new PickUpRequest(orders, translation);
    PickUpRequest request4 = new PickUpRequest(orders, translation);
    PickUpRequest request5 = new PickUpRequest(orders, translation);
    PickUpRequest request6 = new PickUpRequest(orders, translation);
    PickUpRequest request7 = new PickUpRequest(orders, translation);
    PickUpRequest request8 = new PickUpRequest(orders, translation);
    PickUpRequest request9 = new PickUpRequest(orders, translation);
    PickUpRequest request10 = new PickUpRequest(orders, translation);
    PickUpRequest request11 = new PickUpRequest(orders, translation);
    PickUpRequest request12 = new PickUpRequest(orders, translation);
    PickUpRequest request13 = new PickUpRequest(orders, translation);
    PickUpRequest request14 = new PickUpRequest(orders, translation);
    PickUpRequest request15 = new PickUpRequest(orders, translation);
    PickUpRequest request16 = new PickUpRequest(orders, translation);
    PickUpRequest request17 = new PickUpRequest(orders, translation);
    PickUpRequest request18 = new PickUpRequest(orders, translation);
    PickUpRequest request19 = new PickUpRequest(orders, translation);
    PickUpRequest request20 = new PickUpRequest(orders, translation);
    // Create Trucks
    Truck truck = new Truck();
    // truck.setCounter(1);
    ArrayList<Pallet> pal = new ArrayList<Pallet>();
    ArrayList<ArrayList<Pallet>> empty = new ArrayList<ArrayList<Pallet>>();
    Pallet fp = new Pallet();
    Pallet bp = new Pallet();
    for (int i = 0; i < 4; i++) {
      fp.addFascia("5");
    }
    for (int i = 0; i < 4; i++) {
      bp.addFascia("6");
    }
    pal.add(fp);
    pal.add(bp);
    truck.load(request1, pal);
    truck.load(request2, pal);
    truck.load(request3, pal);
    truck.load(request4, pal);
    truck.load(request5, pal);
    truck.load(request6, pal);
    truck.load(request7, pal);
    truck.load(request8, pal);
    truck.load(request9, pal);
    truck.load(request10, pal);
    truck.load(request11, pal);
    truck.load(request12, pal);
    truck.load(request13, pal);
    truck.load(request14, pal);
    truck.load(request15, pal);
    truck.load(request16, pal);
    truck.load(request17, pal);
    truck.load(request18, pal);
    truck.load(request19, pal);
    truck.setAwaitingNext(empty);
    truck.load(request20, pal);
  }

  @Test
  public void testLoad1() {
    String translation = "translation.csv";
    // Create a PickUpRequest
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, translation);
    // Create Trucks
    Truck truck = new Truck();
    // truck.setCounter(1);
    ArrayList<Pallet> pal = new ArrayList<Pallet>();
    truck.load(request, pal);
  }

  @Test
  public void testLoadLeft() {
    Truck truck = new Truck();
    Pallet pallet = new Pallet();
    ArrayList<Pallet> pal = new ArrayList<Pallet>();
    ArrayList<ArrayList<Pallet>> pall = new ArrayList<ArrayList<Pallet>>();
    pal.add(pallet);
    pall.add(pal);
    truck.loadLeft(pal);
    assertEquals(pall, truck.getLeft());
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    truck.loadLeft(pal);
    assertEquals(pall, truck.getLeft());
  }

  @Test
  public void testLoadRight() {
    Truck truck = new Truck();
    Pallet pallet = new Pallet();
    ArrayList<Pallet> pal = new ArrayList<Pallet>();
    ArrayList<ArrayList<Pallet>> pall = new ArrayList<ArrayList<Pallet>>();
    pal.add(pallet);
    pall.add(pal);
    truck.loadRight(pal);
    assertEquals(pall, truck.getRight());
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    pall.add(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    truck.loadRight(pal);
    assertEquals(pall, truck.getRight());
  }

  @Test
  public void testRecordOrder() {
    Truck truck = new Truck();
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    // List<String> skuList = new ArrayList<String>();
    for (Integer i = 1; i < 9; i++) {
      frontPallet.addFascia(i.toString());
      i++;
    }
    for (Integer i = 2; i < 9; i++) {
      backPallet.addFascia(i.toString());
      i++;
    }
    ArrayList<Pallet> pallets = new ArrayList<Pallet>();
    pallets.add(frontPallet);
    pallets.add(backPallet);
    truck.recordOrder(pallets);
    File f = new File("order.csv");
    boolean actual = f.exists();
    boolean expected = true;
    assertEquals(expected, actual);
  }

  @Test
  public void testFullyLoadedFalse() {
    Truck truck = new Truck();
    assertFalse(truck.fullyLoaded());
  }

  @Test
  public void testFullyLoadedTrue() {
    ArrayList<Pallet> pal = new ArrayList<Pallet>();
    Pallet p = new Pallet();
    p.addFascia("1");
    p.addFascia("2");
    p.addFascia("3");
    p.addFascia("4");
    pal.add(p);
    // Create Trucks
    Truck truck2 = new Truck();
    // truck.setCounter(1);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadLeft(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    truck2.loadRight(pal);
    assertEquals(truck2.fullyLoaded(), true);
  }

}
