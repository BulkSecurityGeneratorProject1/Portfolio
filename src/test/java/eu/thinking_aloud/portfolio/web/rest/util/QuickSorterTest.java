package eu.thinking_aloud.portfolio.web.rest.util;

import junit.framework.TestCase;


public class QuickSorterTest extends TestCase {

    public void testGetSorted() throws Exception {
        String input = "3,2,1";
        String expected = "1, 2, 3";

        assert expected.equals(new QuickSorter(input).getSorted());
    }


}
