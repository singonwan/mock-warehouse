package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Pallet;

public class PalletTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPallet() {
		ArrayList<String> a = new ArrayList<String>();
		a.add("1");
		a.add("2");
		a.add("3");
		a.add("4");
		Pallet p = new Pallet();
		p.addFascia("1");
		p.addFascia("2");
		p.addFascia("3");
		p.addFascia("4");
		assertEquals(a,p.getPallet());
	}
	
	@Test
	public void testGetSize() {
		Pallet p = new Pallet();
		p.addFascia("1");
        p.addFascia("2");
        p.addFascia("3");
        p.addFascia("4");
		assertEquals(4,p.size());		
	}
}
