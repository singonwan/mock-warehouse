package unittests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import project.WarehouseFloor;
import project.WarehouseReader;

public class WarehouseReaderTest {

  @Test
  public void testCsvReader() {
    String traversal = "traversal.csv";
    WarehouseReader Reader = new WarehouseReader(traversal);
    String f = "testInitialCsv.csv";
    //Reader.csvReader(f);
    WarehouseFloor whf = Reader.csvReader(f);
    WarehouseFloor expected = new WarehouseFloor(traversal);
    expected.replace("A111", 10);
    expected.replace("B111", 20);
    //need to make equals method for assert Equals to work
    assertEquals(expected, whf);

  }
  
  @Test
  public void testCsvReaderException() {
    String traversal = "traversal.csv";
    WarehouseReader reader = new WarehouseReader(traversal);
    //String traversal = "traversal.csv";
    String a = "blah";
    WarehouseFloor whf = reader.csvReader(a);
  }

  @Test
  public void testFinalCsvWriter() {
    String traversal = "traversal.csv";
    WarehouseReader reader = new WarehouseReader(traversal);
    //File f = new File("final.csv");
    ArrayList<String> kv = new ArrayList<>();
    kv.add("A11110");
    reader.finalCsvWriter(kv);
    String expected = "A11110";
    assertEquals(expected, kv.get(0));
  }
  
  @Test
  public void testFinalCsvWriterSixLess() {
    String traversal = "traversal.csv";
    WarehouseReader reader = new WarehouseReader(traversal);
    //File f = new File("final.csv");
    ArrayList<String> kv = new ArrayList<>();
    kv.add("A1119");
    reader.finalCsvWriter(kv);
    String expected = "A1119";
    assertEquals(expected, kv.get(0));
  }
  
//  @Test
//  public void testFinalCsvException(){
//    String traversal = "traversal.csv";
//    try{
//      ArrayList<String> kv = new ArrayList<>();
//      WarehouseReader reader = new WarehouseReader(traversal);
//      reader.finalCsvWriter(kv);      
//    }
//    catch (FileNotFoundException exc) {
//      
//    }
    
//  }

}
