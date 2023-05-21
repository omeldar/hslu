package ch.hslu.ad.sw13;

import java.util.*;
import java.util.regex.Pattern;

public class OptimalMismatchSearch {
    /**
     * Searches for a pattern in a text using quicksearch
     *
     * @param text to search the pattern in
     * @param pattern to search in the text
     * @return list of index's where appearance of the pattern found in text
     */
    public static List<Integer> optimalMismatchSearch(final String text, final String pattern){
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int range = 256; // ASCII-Range

        final int[] shift = new int[range];

        // init shift-array
        Arrays.fill(shift, patternLength + 1);

        PatternCharacter[] patternArray = new PatternCharacter[patternLength];

        // overwrite fields according to pattern
        for (int i = 0; i < patternLength; i++){
            shift[pattern.charAt(i)] = patternLength - i;
            patternArray[i] = new PatternCharacter(pattern.charAt(i), i);    // Initialize patternDictionary to match pattern
        }

        int textIndex = 0;
        int patternIndex = 0;
        List<Integer> matches = new ArrayList<>();
        do {
            if (text.charAt(textIndex + patternArray[patternIndex].PatternIndex) ==
                    pattern.charAt(patternArray[patternIndex].PatternIndex)) {
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
                    if (text.charAt(textIndex + patternLength) >= 256){
                        textIndex++;
                        continue;
                    }
                    textIndex += shift[text.charAt(textIndex + patternLength)]; // shift pattern
                    moveArrayItemUp(patternArray, patternIndex);
                    patternIndex = 0;
                } else {
                    break;
                }
            }
        } while ((textIndex + patternLength) <= textLength); // when not enough in text left to find a match -> leave loop
        return matches;
    }

    /**
     * Moves an item in an array up by one position
     * Does not move any items when item to move up already at index 0
     *
     * @param array to switch item positions in
     * @param position1 position (index) of first item
     */
    private static void moveArrayItemUp(PatternCharacter[] array, int position1){
        if (position1 > 0) {
            PatternCharacter tmp;
            tmp = array[position1];
            array[position1] = array[position1 -1];
            array[position1 - 1] = tmp;
        }
    }
}
