package project;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * The generic software that is used to find the location of the 8 fascias
 * required by an order.
 * 
 * @author thioshar
 *
 */
public class WarehousePicking {
  /**
   * Returns the location of the 8 fascias required by the PickUpRequest
   * 
   * @param request the PickUpReuqest
   * @return the location of the 8 fascias
   */
  public ArrayList<String> optimize(PickUpRequest request) {
    ArrayList<String> locations = new ArrayList<String>();
    List<String> skus = request.getSkus();
    ListIterator<String> iterator = skus.listIterator();

    while (iterator.hasNext()) {
      locations.add(iterator.next());
    }
    return locations;
  }
}
