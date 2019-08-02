package unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.WarehouseFloor;

public class WarehouseFloorTest {

  @Test
  public void testPicking() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    // 5
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    // 10
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    // 15
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    // 20
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    f.picking("A000");
    // 25
  }

  @Test
  public void testReplenishRequest() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    assertEquals("A000", f.replenishRequest("A000"));
  }

  @Test
  public void testReplenish() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    f.replenish("A000");
  }

  @Test
  public void testReplace() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    f.replace("A001", 20);
  }

  @Test
  public void testReturnKeyValue() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    ArrayList<String> v = f.returnKeyValue();
    assertEquals(48, v.size());
    // help I don't actually know what every single value should be
  }

  @Test
  public void testReturnLocation() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    String l = f.returnLocation("1");
    assertEquals("A000", l);
  }
  
  @Test
  public void testfilenotfoundReturnLocation() {
    String traversal = "suh";
    WarehouseFloor f = new WarehouseFloor(traversal);
    f.returnLocation("1");
  }
  
  @Test
  public void testEquals() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    WarehouseFloor wf = new WarehouseFloor(traversal);
    assertEquals(wf,f);
    assertTrue(wf.equals(wf));
  }

  @Test
  public void testNotEquals() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    WarehouseFloor wf = new WarehouseFloor("hi");
    
    String b = null;
    assertNotEquals(f,b);
    wf.setFloor(null);
    assertNotEquals(wf,f);
    
    
    WarehouseFloor gg = new WarehouseFloor(traversal);
    WarehouseFloor ff = new WarehouseFloor(traversal);
    gg.replace("A000", 20);
    assertNotEquals(gg,ff);

    
  }
  @Test
  public void testGetAmountTrue() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    int actual = f.getAmount("A000");
    int expected = 30;
    assertEquals(actual, expected);    
  }
  @Test
  public void testGetAmountFalse() {
    String traversal = "traversal_table.csv";
    WarehouseFloor f = new WarehouseFloor(traversal);
    int actual = f.getAmount("A000");
    int expected = 20; //Value of actual should be 30
    assertNotEquals(actual, expected);    
  }
}
