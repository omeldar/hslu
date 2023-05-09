package ch.hslu.ad.sw12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeaTest {
    @Test
    public void isWordLanguageWithCorrectWordTest(){
        //arrange
        String input = "010";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void isWordLanguageWithCorrectLongWordTest(){
        //arrange
        String input = "0101110111110";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void isWordLanguageWithIncorrectWordTest(){
        //arrange
        String input = "110";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isWordLanguageWithJust0Test(){
        //arrange
        String input = "0";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void isWordLanguageState2Test(){
        //arrange
        String input = "01";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isWordLanguageState3Test(){
        //arrange
        String input = "011";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isWordLanguageState2AgainTest(){
        //arrange
        String input = "0111";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void isWordLanguageState4Test(){
        //arrange
        String input = "01110";

        //act
        boolean result = Dea.isWordLanguage(input);

        //assert
        Assertions.assertTrue(result);
    }
}