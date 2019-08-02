package unittests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import project.Pallet;
import project.PickUpRequest;

public class PickUpRequestTest {
  
  private String translation = "translation.csv";

  @Test
  public void testBadInit() {
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> o1 = new ArrayList<String>();
    o1.add("S");
    o1.add("White");
    List<String> o2 = new ArrayList<String>();
    o2.add("SE");
    o2.add("White");
    orders.add(o1);
    orders.add(o2);
    PickUpRequest request = new PickUpRequest(orders, translation);
  }

  @Test
  public void testGetCounter() {
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> o1 = new ArrayList<String>();
    o1.add("S");
    o1.add("White");
    List<String> o2 = new ArrayList<String>();
    o2.add("SE");
    o2.add("White");
    List<String> o3 = new ArrayList<String>();
    o3.add("SES");
    o3.add("White");
    List<String> o4 = new ArrayList<String>();
    o4.add("SEL");
    o4.add("White");
    orders.add(o1);
    orders.add(o2);
    orders.add(o3);
    orders.add(o4);
    PickUpRequest request = new PickUpRequest(orders, translation);
    assertEquals(0, request.getCounter());
  }

  @Test
  public void testSettersAndGetters() {
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> o1 = new ArrayList<String>();
    o1.add("S");
    o1.add("White");
    List<String> o2 = new ArrayList<String>();
    o2.add("SE");
    o2.add("White");
    List<String> o3 = new ArrayList<String>();
    o3.add("SES");
    o3.add("White"); 
    List<String> o4 = new ArrayList<String>();
    o4.add("SEL");
    o4.add("White");
    orders.add(o1);
    orders.add(o2);
    orders.add(o3);
    orders.add(o4);
    PickUpRequest request = new PickUpRequest(orders, translation);
    request.setPicked(true);
    assertTrue(request.isPicked());
    request.setSequenced(true);
    assertTrue(request.isSequenced());
    request.setLoaded(true);
    assertTrue(request.isLoaded());
  }

  @Test
  public void testGetSkus() {
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> o1 = new ArrayList<String>();
    o1.add("S");
    o1.add("White");
    List<String> o2 = new ArrayList<String>();
    o2.add("SE");
    o2.add("White");
    List<String> o3 = new ArrayList<String>();
    o3.add("SES");
    o3.add("White");
    List<String> o4 = new ArrayList<String>();
    o4.add("SEL");
    o4.add("White");
    orders.add(o1);
    orders.add(o2);
    orders.add(o3);
    orders.add(o4);
    PickUpRequest request = new PickUpRequest(orders, translation);
    List<String> nums = new ArrayList<String>();
    nums.add("1");
    nums.add("2");
    nums.add("3");
    nums.add("4");
    nums.add("5");
    nums.add("6");
    nums.add("7");
    nums.add("8");
    assertEquals(nums, request.getSkus());
  }

  @Test 
  
  public void testNonExistentFile() {
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> order = new ArrayList<String>();
    order.add("SES");
    order.add("White");
    orders.add(order);
    orders.add(order);
    orders.add(order);
    orders.add(order);
    PickUpRequest request = new PickUpRequest(orders, "dne");
    List<String> expected = new ArrayList<>();
    assertEquals(expected, request.getSkus());
  }

  @Test
  public void testGetPickUpID() {
	//Hack, don't do this
	PickUpRequest.setIdCounter(0);
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> o1 = new ArrayList<String>();
    o1.add("S");
    o1.add("White");
    List<String> o2 = new ArrayList<String>();
    o2.add("SE");
    o2.add("White");
    List<String> o3 = new ArrayList<String>();
    o3.add("SES");
    o3.add("White");
    List<String> o4 = new ArrayList<String>();
    o4.add("SEL");
    o4.add("White");
    orders.add(o1);
    orders.add(o2);
    orders.add(o3);
    orders.add(o4);
    PickUpRequest request = new PickUpRequest(orders, translation);
    assertEquals(0, request.getPickUpId());
  }

  @Test
  public void testGetPallet() {
    List<List<String>> orders = new ArrayList<List<String>>();
    List<String> o1 = new ArrayList<String>();
    o1.add("S");
    o1.add("White");
    List<String> o2 = new ArrayList<String>();
    o2.add("SE");
    o2.add("White");
    List<String> o3 = new ArrayList<String>();
    o3.add("SES");
    o3.add("White");
    List<String> o4 = new ArrayList<String>();
    o4.add("SEL");
    o4.add("White");
    orders.add(o1);
    orders.add(o2);
    orders.add(o3);
    orders.add(o4);
    PickUpRequest request = new PickUpRequest(orders, translation);
    Pallet p = new Pallet();
    List<Pallet> palletList = new ArrayList<Pallet>();
    palletList.add(p);
    request.addPallet(p);
    assertEquals(palletList, request.getPallet());
  }


}
