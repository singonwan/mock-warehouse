package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import checkers.LoadedChecker;
import project.Pallet;
import project.PickUpRequest;
import project.Truck;

public class LoadedCheckerTest {
  
  private String translation = "translation.csv";

  @Test
  public void testCheckTrue() {
    Truck truck = new Truck();
    LoadedChecker pchecker = new LoadedChecker();
    
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
    
    //Adding the "SKU"
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    List<String> skuList = new ArrayList<String>();
    for (Integer i = 0; i < 4; i++) {
      frontPallet.addFascia("5");
    }
    for (Integer i = 0; i < 4; i++) {
      backPallet.addFascia("6");
    }
    for (Integer i = 0; i < 4; i++) {
      skuList.add("5");
      skuList.add("6");
    }
    request.addPallet(frontPallet);
    request.addPallet(backPallet);
    boolean expected = true;
    boolean actual = pchecker.check(request, skuList, truck);
    assertEquals(expected, actual);
  }
  @Test
  public void testCheckFalse() {
    Truck truck = new Truck();
    LoadedChecker pchecker = new LoadedChecker();
    
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
    
    //Adding the "SKU"
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    List<String> skuList = new ArrayList<String>();
    for (Integer i = 0; i < 8; i++) {
      frontPallet.addFascia(i.toString());
      i++;
    }
    for (Integer i = 2; i < 9; i++) {
      backPallet.addFascia(i.toString());
      i++;
    }
    for (Integer i = 1; i < 9; i++) {
      skuList.add(i.toString());
    }
    request.addPallet(frontPallet);
    request.addPallet(backPallet);
    boolean expected = false;
    boolean actual = pchecker.check(request, skuList, truck);
    assertEquals(expected, actual);
  }
}
