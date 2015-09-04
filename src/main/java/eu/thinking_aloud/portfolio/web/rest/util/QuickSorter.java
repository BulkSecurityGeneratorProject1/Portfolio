package eu.thinking_aloud.portfolio.web.rest.util;

import java.util.Arrays;

/**
 * performs quicksort
 */
public class QuickSorter {

    int[] unsorted, tmp;
    String sorted;
    long executionTime;

    public QuickSorter(String unsorted) {
        this.unsorted = splitAtComma(unsorted);

        long timeBefore = System.nanoTime();
        sort();
        executionTime = (System.nanoTime() - timeBefore) / 1000;
    }

    private int[] splitAtComma(String input) {
        String[] items = input.split(",");
        int[] result = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                result[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
            }
        }
        return result;
    }

    private void sort() {
        tmp = unsorted;
        quickSort(0, unsorted.length - 1);
        sorted = Arrays.toString(tmp);
        sorted = sorted.substring(1, sorted.length() - 1);
        System.out.println("sorted: " + sorted);
    }

    private void quickSort(int lo, int hi) {
        int i = lo, j = hi;
        int x = tmp[(lo + hi) / 2];

        // splitAtComma
        while (i <= j) {
            while (tmp[i] < x) {
                i++;
            }
            while (tmp[j] > x) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }

        // recursion
        if (lo < j) {
            quickSort(lo, j);
        }
        if (i < hi) {
            quickSort(i, hi);
        }
    }

    private void swap(int i, int j) {
        int t = tmp[i];
        tmp[i] = tmp[j];
        tmp[j] = t;
    }

    public String getSorted() {
        return sorted;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
