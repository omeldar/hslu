package ch.hslu.ad.sw13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuickSearch {
    /**
     * Searches for a pattern in a text using quicksearch
     *
     * @param text to search the pattern in
     * @param pattern to search in the text
     * @return list of index's where appearance of the pattern found in text
     */
    public static List<Integer> quickSearch(final String text, final String pattern){
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int range = 256; // ASCII-Range

        final int[] shift = new int[range];

        // init shift-array
        Arrays.fill(shift, patternLength + 1);

        // overwrite fields according to pattern
        for (int i = 0; i < patternLength; i++){
            shift[pattern.charAt(i)] = patternLength - i;
        }

        int textIndex = 0;
        int patternIndex = 0;
        List<Integer> matches = new ArrayList<>();
        do {
            if (text.charAt(textIndex + patternIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
                // if pattern completely found, patternIndex is here 'OutOfBounds' (1 too high),
                // that's why when comparing we do not need to subtract 1 from patternLength
                if (patternIndex == patternLength){
                    matches.add(textIndex);
                    patternIndex = 0;   // resetting to be able to find more matches in the text
                    textIndex += 1; // shift pattern
                }
            } else {
                if ((textIndex + patternLength) < textLength) {
                    textIndex += shift[text.charAt(textIndex + patternLength)]; // shift pattern
                    patternIndex = 0;
                } else {
                    break;
                }
            }
        } while ((textIndex + patternLength) <= textLength); // when not enough in text left to find a match -> leave loop
        return matches;
    }
}
