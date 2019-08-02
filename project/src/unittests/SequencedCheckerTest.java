package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import checkers.LoadedChecker;
import checkers.SequencedChecker;
import project.Pallet;
import project.PickUpRequest;
import project.Truck;

public class SequencedCheckerTest {
  
  private String translation = "translation.csv";

  @Test
  public void testAdd() {
    SequencedChecker schecker = new SequencedChecker();
    List<String> skuList = new ArrayList<String>();
    for (Integer i = 1; i < 9; i++) {
      skuList.add(i.toString());
    }
    schecker.sequence(skuList);
    //Making the pallets
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    for (Integer i = 0; i < 8; i++) {
      frontPallet.addFascia(i.toString());
      i++;
    }
    for (Integer i = 2; i < 9; i++) {
      backPallet.addFascia(i.toString());
      i++;
    }
    assertEquals(schecker.getFrontPallet().size(),frontPallet.size());
    assertEquals(schecker.getBackPallet().size(),backPallet.size() );
  }

  @Test
  public void testCheckTrue() {
    SequencedChecker pchecker = new SequencedChecker();
    
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
    boolean expected = true;
    boolean actual = pchecker.check(request, skuList);
    assertEquals(expected, actual);
  }
  @Test
  public void testCheckFalseBack() {
    SequencedChecker pchecker = new SequencedChecker();
    
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
    List<String> skuList = new ArrayList<String>();
    List<String> skuListWrong = new ArrayList<String>();
    for (Integer i = 1; i < 9; i++) {
      skuList.add(i.toString());
    }
    skuListWrong.add("1");
    for (Integer i = 0; i < 7; i++) {
      skuListWrong.add(i.toString());
    }
    pchecker.sequence(skuList);
    boolean expected = false;
    boolean actual = pchecker.check(request, skuListWrong);
    assertEquals(expected, actual);
  }
  @Test
  public void testCheckFalseFront() {
    SequencedChecker pchecker = new SequencedChecker();
    
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
    List<String> skuList = new ArrayList<String>();
    List<String> skuListWrong = new ArrayList<String>();
    for (Integer i = 1; i < 9; i++) {
      skuList.add(i.toString());
    }
    for (Integer i = 0; i < 8; i++) {
      skuListWrong.add(i.toString());
    }
    pchecker.sequence(skuList);
    boolean expected = false;
    boolean actual = pchecker.check(request, skuListWrong);
    assertEquals(expected, actual);
  }
  @Test
  public void testFirstCheckFalse(){
SequencedChecker pchecker = new SequencedChecker();
    
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
    List<String> skuList = new ArrayList<String>();
    List<String> skuListWrong = new ArrayList<String>();
    for (Integer i = 1; i < 9; i++) {
      skuList.add(i.toString());
    }
    for (Integer i = 0; i < 8; i++) {
      skuListWrong.add(i.toString());
    }
    pchecker.sequence(skuList);
    boolean expected = false;
    boolean actual = pchecker.firstCheck(request, skuListWrong);
    assertEquals(expected, actual);
  }
}
