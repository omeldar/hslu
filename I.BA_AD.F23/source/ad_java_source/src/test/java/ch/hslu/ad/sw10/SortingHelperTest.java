package ch.hslu.ad.sw10;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SortingHelperTest {
    @Test
    public void quickSortTest() {
        //arrange
        char[] dataArray = new char[] {'G', 'A', 'F', 'Z', 'T', 'M', 'J', 'I'};
        char[] expected = new char[] {'A', 'F', 'G', 'I', 'J', 'M', 'T', 'Z'};

        //act
        dataArray = SortingHelper.quickSort(dataArray, 0, dataArray.length -1);

        //assert
        Assertions.assertArrayEquals(expected, dataArray);
    }
}
