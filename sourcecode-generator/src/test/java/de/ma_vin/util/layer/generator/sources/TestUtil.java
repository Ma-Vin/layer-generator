package de.ma_vin.util.layer.generator.sources;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtil {

    public static <T extends Comparable<T>> void checkComparisonOfSortedList(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    assertEquals(0, list.get(i).compareTo(list.get(i)), String.format("same values should not differ i=%d, j=%d)", i, j));
                } else if (i < j) {
                    assertTrue(list.get(i).compareTo(list.get(j)) <= -1, String.format("i=%d one should be before j=%d one", i, j));
                } else {
                    assertTrue(1 <= list.get(i).compareTo(list.get(j)), String.format("i=%d one should be after j=%d one", i, j));
                }
            }
        }
    }

    public static <T extends Comparable<T>> void checkSortingOfSortedList(List<T> list) {
        T first = list.get(0);
        T last = list.get(list.size() - 1);

        Collections.sort(list);
        assertEquals(first, list.get(0), "Wrong first element after sorting");
        assertEquals(last, list.get(list.size() - 1), "Wrong last element after sorting");
    }

    public static void checkList(List<String> expectedList, List<String> actualList) {
        assertNotNull(actualList, "There should be any result");
        assertEquals(expectedList.size(), actualList.size(), "Wrong number of lines");
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i), "Wrong list element at position " + i);
        }
    }
}
