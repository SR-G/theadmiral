package net.coljac.pirates.tests;

import junit.framework.TestCase;
import net.coljac.pirates.gui.ShipSorter;

/**
 * By Colin Jacobs, colin@q9software.com
 * Date: Mar 12, 2006
 */
public class SortTest extends TestCase {

  public void testSortOrder() throws Exception {

    ShipSorter sorter = new ShipSorter();
    sorter.setSort(11);
    sorter.setSort(2);
    sorter.setSort(7);
    sorter.setSort(7);
    sorter.setSort(9);
    int[] sorts = sorter.getSortOrder();

    //     sortOrder = new int[] {3, 4, 5, 1, 2, 6, 7, 8, 9, 10, 11, 12};
    assertEquals(9, sorts[0]);
    assertEquals(-7, sorts[1]);
    assertEquals(2, sorts[2]);
    assertEquals(11, sorts[3]);
    assertEquals(3, sorts[4]);
    assertEquals(4, sorts[5]);
    assertEquals(5, sorts[6]);
    assertEquals(1, sorts[7]);
    assertEquals(6, sorts[8]);
    assertEquals(8, sorts[9]);
    assertEquals(10, sorts[10]);
    assertEquals(12, sorts[11]);

  }
}
