package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import project.PickUpRequest;
import worker.Picker;
import worker.Worker;

public class PickerTest {

	@Test
	public void testGetName() {
		Picker w1 = new Picker("Alice");
		assertEquals("Alice", w1.getName());
	    Worker.setWorkerId(0);
	}
	@Test
	public void testGetWorkerID() {
		Picker w1 = new Picker("Alice");
		assertEquals(2, w1.getWorkerId());
	    Worker.setWorkerId(0);
	}
	@Test
	public void testGetFascias() {
		Picker w1 = new Picker("Alice");
		ArrayList<String> mockFascias = new ArrayList<String>();
		mockFascias.add("1");
		mockFascias.add("2");
		mockFascias.add("3");
		w1.addFascia("1");
        w1.addFascia("2");
        w1.addFascia("3");
		assertEquals(mockFascias, w1.getFascias());
		w1.reset();
		assertEquals(new ArrayList<Integer>(), w1.getFascias());
	}
	@Test
	public void testIsReady() {
		Picker w1 = new Picker("Alice");
		w1.changeReadyStatus(false);
		assertFalse(w1.isReady());
		w1.changeReadyStatus(true);
		assertTrue(w1.isReady());
	}
	@Test
	public void testGetRequest() {
	  String translation = "translation.csv";
		Picker w1 = new Picker("Alice");
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
		w1.assignRequest(request);
		assertEquals(request, w1.getRequest());
	}

}
