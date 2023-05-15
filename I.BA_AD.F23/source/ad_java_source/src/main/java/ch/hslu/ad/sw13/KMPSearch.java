package ch.hslu.ad.sw13;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch {
    /**
     * Returns the pattern shifting array with the prefix lengths
     * @param pattern to search for in text
     * @return Prefix lengths (shifting values) of the prefix's that are suffix's in the pattern in an int array
     */
    private static int[] calcPrefixLength(final String pattern) {
        int patternLength = pattern.length();

        // create array 1 bigger than pattern length to be able to store -1 at position 0
        int[] prefixLengthArray = new int[patternLength + 1];
        prefixLengthArray[0] = -1;    // first value always -1
        prefixLengthArray[1] = 0;     // second value always 0

        /*
        * Set start prefix length to 0 and set pattern index to 1 to avoid
        * checking prefix length for -1 on pattern index 0
        * This has been written into the array above, but we don't loop over it.
        */
        int prefixLength = 0;
        int i = 1;

        while(i < patternLength) {
            if (pattern.charAt(prefixLength) == pattern.charAt(i)){ // if prefix found
                prefixLength++; // increase current prefix length value
                i++;            // increase prefix length array index
                prefixLengthArray[i] = prefixLength;    // save prefix length at current position to array
            } else if (prefixLength > 0){
                /*
                * Start decrease prefix length if no match to ensure not to skip potential matches
                * We move to the position with the previous longest proper prefix that is
                * also a suffix. This allows to efficiently search for the next potential match.
                */
                prefixLength = prefixLengthArray[prefixLength];
            } else {
                i++;    // continue searching by increasing index
                prefixLengthArray[i] = 0;   // set next prefix length value to 0
            }
        }
        return prefixLengthArray;
    }

    /**
     * Search for a pattern in a text
     * @param text to search the pattern in
     * @param pattern to search in text
     * @return List of indexes where there are pattern matches in the text
     */
    public static List<Integer> search(final String text, final String pattern) {
        int textIndex = 0;  // the position of the current characters in text
        int patternIndex = 0; // the position of the current characters in pattern

        int textLength = text.length();
        int patternLength = pattern.length();

        List<Integer> matches = new ArrayList<>();  // list of all found matches of the pattern in the text
        int[] prefixLength = calcPrefixLength(pattern); // get prefix length array (shifting values)

        while(textIndex < textLength) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)){    // if pattern character match found
                patternIndex++;
                textIndex++;

                if (patternIndex == patternLength) {
                    // occurrence found, if only first occurrence is needed then you could halt here
                    matches.add(textIndex - patternIndex);
                    patternIndex = prefixLength[patternIndex];
                }
            } else {
                patternIndex = prefixLength[patternIndex];  // decrease patternIndex

                // as soon as -1 is hit in the shifting values continue searching in text for
                // other appearance of pattern
                if (patternIndex < 0) {
                    textIndex++;
                    patternIndex++;
                }
            }
        }
        return matches;
    }
}
