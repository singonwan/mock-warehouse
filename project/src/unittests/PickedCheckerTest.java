package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import checkers.PickedChecker;
import project.PickUpRequest;

public class PickedCheckerTest {
  
  private String translation = "translation.csv";

  @Test
  public void testCheckPickerTrue() {
	PickedChecker pchecker = new PickedChecker();
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
    boolean actual = pchecker.checkPicker(request, "1");
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckPickerFalse() {
		PickedChecker pchecker = new PickedChecker();
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
	    boolean expected = false;
	    boolean actual = pchecker.checkPicker(request, "9");
	    assertEquals(expected, actual);
  }
}
