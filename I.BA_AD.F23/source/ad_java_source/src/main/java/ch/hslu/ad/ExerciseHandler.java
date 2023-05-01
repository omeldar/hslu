package ch.hslu.ad;

import ch.hslu.ad.sw10.SortingHelper;

public class ExerciseHandler {
    public static void main(String[] args) {
        char[] chars = SortingHelper.randomChars(300000);
        long start = System.currentTimeMillis();
        char[] res = SortingHelper.quickSort(chars);
        long end = System.currentTimeMillis();
        System.out.println("Duration in ms: " + (end - start));
    }
}
