package ch.hslu.ad.sw13;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PatternSearchTest {

    @Test
    void stateSearchOnlyPattern() {
        // arrange
        String input = "ANANAS";

        //act
        int result = PatternSearch.stateSearch(input);

        //assert
        Assertions.assertEquals(0, result);
    }

    @Test
    void stateSearchPatternAtEnd() {
        // arrange
        String input = "SSSANANAS";

        //act
        int result = PatternSearch.stateSearch(input);

        //assert
        Assertions.assertEquals(3, result);
    }

    @Test
    void stateSearchSOfPatternAtEndMissing() {
        // arrange
        String input = "SSSANANA";

        //act
        int result = PatternSearch.stateSearch(input);

        //assert
        Assertions.assertEquals(-1, result);
    }

    @Test
    void stateSearchSimilarLikePatternButPatternNotIncluded() {
        // arrange
        String input = "ANANANNANNAANANANANAAANANAASNANS";

        //act
        int result = PatternSearch.stateSearch(input);

        //assert
        Assertions.assertEquals(-1, result);
    }

    @Test
    void stateSearchSimilarLikePatternPatternIncluded() {
        // arrange
        String input = "ANANANNANNAANANANANAAANANANANASAASNANS";

        //act
        int result = PatternSearch.stateSearch(input);

        //assert
        Assertions.assertEquals(25, result);
    }
}