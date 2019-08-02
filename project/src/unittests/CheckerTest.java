package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import checkers.Checker;
import project.Pallet;
import project.PickUpRequest;

public class CheckerTest {
  
  private String translation = "translation.csv";

  @Test
  public void testCheckPickerSame() {
    Checker checker = new Checker();
/*
    ArrayList<String> skuCollected = new ArrayList<String>();
    List<String> skuList = new ArrayList<String>();
    skuCollected.add("8");
    skuList.add("8");
*/
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
   
    boolean expected = true;
    boolean actual = checker.checkPicker(request, "1");
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckPickerDifferent() {
    Checker checker = new Checker();
/*
    ArrayList<String> skuCollected = new ArrayList<String>();
    List<String> skuList = new ArrayList<String>();
    skuCollected.add("8");
    skuList.add("9");
*/
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
   
    boolean expected = false;
    boolean actual = checker.checkPicker(request, "9");
    assertEquals(expected, actual);
  }

  @Test
  public void testCheck() {
    Checker checker = new Checker();
    List<String> skuList = new ArrayList<String>();
    //for (Integer i = 1; i < 9; i++) {
    //  skuList.add(i.toString());
    //}
    skuList.add("1");
    skuList.add("2");
    skuList.add("3");
    skuList.add("4");
    skuList.add("5");
    skuList.add("6");
    skuList.add("7");
    skuList.add("8");
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    //for (Integer i = 1; i < 9; i++) {
    frontPallet.addFascia("1");
    frontPallet.addFascia("3");
    frontPallet.addFascia("5");
    frontPallet.addFascia("7");
    //i++;
    //}
    backPallet.addFascia("2");
    backPallet.addFascia("4");
    backPallet.addFascia("6");
    backPallet.addFascia("9");
    checker.setFrontPallet(frontPallet);
    checker.setBackPallet(backPallet);
    
    boolean expected = false;
    boolean actual = checker.check(skuList);
    assertEquals(expected, actual);
    Pallet backPallet2 = new Pallet();
    backPallet2.addFascia("2");
    backPallet2.addFascia("4");
    backPallet2.addFascia("6");
    backPallet2.addFascia("8");
    checker.setBackPallet(backPallet2);
    
    expected = true;
    actual = checker.check(skuList);
    assertEquals(expected, actual);
  }

  @Test
  public void testReset() {
    Checker checker = new Checker();
    Pallet frontPallet = new Pallet();
    Pallet backPallet = new Pallet();
    frontPallet.addFascia("8");
    backPallet.addFascia("9");
    checker.setFrontPallet(frontPallet);
    checker.setBackPallet(backPallet);
    checker.reset();
    int expected = new Pallet().size();
    assertEquals(checker.getFrontPallet().size(), expected);
  }

  @Test
  public void testGetFrontPallet() {
    Checker checker = new Checker();
    Pallet frontPallet = new Pallet();
    frontPallet.addFascia("8");
    checker.setFrontPallet(frontPallet);
    Pallet expected = frontPallet;
    Pallet actual = checker.getFrontPallet();
    assertEquals(expected, actual);
  }

  @Test
  public void testSetFrontPallet() {
    Checker checker = new Checker();
    Pallet frontPallet = new Pallet();
    frontPallet.addFascia("8");
    checker.setFrontPallet(frontPallet);
    Pallet expected = frontPallet;
    Pallet actual = checker.getFrontPallet();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetBackPallet() {
    Checker checker = new Checker();
    Pallet backPallet = new Pallet();
    backPallet.addFascia("8");
    checker.setBackPallet(backPallet);
    Pallet expected = backPallet;
    Pallet actual = checker.getBackPallet();
    assertEquals(expected, actual);
  }

  @Test
  public void testSetBackPallet() {
    Checker checker = new Checker();
    Pallet backPallet = new Pallet();
    backPallet.addFascia("8");
    checker.setBackPallet(backPallet);
    Pallet expected = backPallet;
    Pallet actual = checker.getBackPallet();
    assertEquals(expected, actual);
  }

}
