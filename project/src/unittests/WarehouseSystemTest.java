package unittests;

import static org.junit.Assert.*;

import project.WarehouseSystem;

import java.io.File;

import org.junit.Test;

public class WarehouseSystemTest {

  @Test
  public void testMain() {
    WarehouseSystem system = new WarehouseSystem();
    File f = new File("final.csv");
    boolean actual= f.exists();
    boolean expected = true;
    assertEquals(expected, actual);
    String[] args = {"16orders.txt","traversal_table.csv","translation.csv"};
    WarehouseSystem.main(args);
  }
  @Test
  public void testRead() {
    WarehouseSystem system = new WarehouseSystem();
    String testpath = "a/r/t/c";
    String actual = system.read(testpath);
    String expected = "c";
    assertEquals(expected, actual);
        
  }

}
