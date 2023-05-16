package ch.hslu.ad.sw13;

import java.util.*;

public class OptimalMismatchSearch {
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

        Map<Character, Integer> shiftTable = new HashMap<>();

        for (int i = 0; i < patternLength - 1; i++) {
            char currentChar = pattern.charAt(i);
            int lastOccurrence = i; // Default value if character doesn't occur again

            for (int j = i + 1; j < patternLength; j++) {
                if (pattern.charAt(j) == currentChar) {
                    lastOccurrence = j;
                }
            }
            shiftTable.put(currentChar, patternLength - lastOccurrence - 1);
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
                    char mismatchedChar = text.charAt(textIndex + patternLength);
                    int shift = shiftTable.getOrDefault(mismatchedChar, patternLength + 1);
                    textIndex += shift;
                    patternIndex = 0;

                    // Update the shifting table
                    shiftTable.remove(mismatchedChar);
                    shiftTable.put(mismatchedChar, 0); // Move mismatchedChar to the top of the shifting table
                } else {
                    break;
                }
            }
        } while ((textIndex + patternLength) <= textLength); // when not enough in text left to find a match -> leave loop
        return matches;
    }
}
