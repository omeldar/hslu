package ch.hslu.ad.sw01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GGTRechnerTest {
    @Test
    void SW01_E1_1_TestIterativ1(){
        int a = 16;
        int b = 12;
        int ggt = GGTRechner.ggtIterativ1(a, b);
        assertEquals(4, ggt);
    }

    @Test
    void SW01_E1_1_TestIterativ2(){
        int a = 16;
        int b = 12;
        int ggt = GGTRechner.ggtIterativ2(a, b);
        assertEquals(4, ggt);
    }

    @Test
    void SW01_E1_1_TestRekursiv(){
        int a = 16;
        int b = 12;
        int ggt = GGTRechner.ggtRekursiv(a, b);
        assertEquals(4, ggt);
    }
}
