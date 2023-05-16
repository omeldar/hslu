package ch.hslu.ad.sw13;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class QuickSearchTest {

    @Test
    void quickSearchSingleMatch() {
        // arrange
        String text = "AOWIDANANASWD";
        String pattern = "ANANAS";
        List<Integer> expected = Arrays.asList(new Integer[] {5});

        // act
        List<Integer> result = QuickSearch.quickSearch(text, pattern);

        // assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void searchMultipleMatchesTest() {
        // arrange
        String text = "abbbcaabcccbccabc";
        String pattern = "abc";
        List<Integer> expected = Arrays.asList(new Integer[] {6, 14});

        // act
        List<Integer> result = QuickSearch.quickSearch(text, pattern);

        // assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void searchNoMatchesTest() {
        // arrange
        String text = "abbbcaabcccbccabc";
        String pattern = "def";
        List<Integer> expected = Arrays.asList(new Integer[] {});

        // act
        List<Integer> result = QuickSearch.quickSearch(text, pattern);

        // assert
        Assertions.assertEquals(expected, result);
    }
}