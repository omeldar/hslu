package ch.hslu.ad.sw10;

import java.util.Random;

/**
 * SortingHelper to help with exercise D1 - 2 - Quicksort
 */
public class SortingHelper {
    /**
     * Exchanges two chars in the array
     *
     * @param a Char-Array
     * @param firstIndex Index of first char
     * @param secondIndex Index of second char
     */
    private static final void exchange(final char[] a,
                                       final int firstIndex,
                                       final int secondIndex) {
        char tmp;
        tmp = a[firstIndex];
        a[firstIndex] = a[secondIndex];
        a[secondIndex] = tmp;
    }

    /**
     * Get an array of random characters
     * @param length Length of the array with random characters
     * @return an array of random characters
     */
    public static final char[] randomChars(final int length) {
        char[] abc = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        char[] arr_to_return = new char[length];
        for (int i = 0; i < length; i++) {
            arr_to_return[i] = abc[random.nextInt(abc.length -1)];
        }
        return arr_to_return;
    }

    /**
     * Quicksort method to sort a char array with the quicksort method
     * @param a array to sort
     * @return the sorted array
     */
    public static final char[] quickSort(final char[] a){
        return quickSort(a, 0, a.length -1);
    }

    /**
     * Quicksort method to sort a char array with the quicksort method
     * @param a array to sort
     * @return the sorted array
     */
    public static final char[] quickSort(final char[] a, final int left, final int right) {
        int up = left; // linke Grenze
        int down = right - 1; // rechte Grenze (ohne Trennelement)
        char t = a[right]; // rechtes Element als Trennelement
        boolean allChecked = false;
        do {
            while (a[up] < t) {
                up++; // suche grösseres (>=) Element von links an
            }
            while ((a[down] >= t) && (down > up)) {
                down--; // suche echt kleineres (<) Element von rechts an
            }
            if (down > up) { // solange keine Überschneidung
                exchange(a, up, down);
                up++; down--; // linke und rechte Grenze verschieben
            } else {
                allChecked = true; // Austauschen beendet
            }
        } while (!allChecked);
        exchange(a, up, right); // Trennelement an endgültige Position (a[up])
        if (left < (up - 1)) quickSort(a, left, (up - 1)); // linke Hälfte
        if ((up + 1) < right) quickSort(a, (up + 1), right); // rechte Hälfte, ohne T’Elt.
        return a;
    }
}
