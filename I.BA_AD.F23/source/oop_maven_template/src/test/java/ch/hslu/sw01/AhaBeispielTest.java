package ch.hslu.sw01;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AhaBeispielTest {
    @Test
    void SW01_E1_2_CountTaskRunsWith4(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 4;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(12, ahaBeispiel.Task2RunCount);
        assertEquals(32, ahaBeispiel.Task3RunCount);
    }
    @Test
    void SW01_E1_2_CountTaskRunsWith8(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 8;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(24, ahaBeispiel.Task2RunCount);
        assertEquals(128, ahaBeispiel.Task3RunCount);
    }
    @Test
    void SW01_E1_2_CountTaskRunsWith32(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 32;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(96, ahaBeispiel.Task2RunCount);
        assertEquals(2048, ahaBeispiel.Task3RunCount);
    }

    @Test
    void SW01_E1_2_CountTaskRunsWith33(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 33;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(99, ahaBeispiel.Task2RunCount);
        assertEquals(2178, ahaBeispiel.Task3RunCount);
    }

    @Test
    void SW01_E1_2_CountTaskRunsWith40(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 40;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(120, ahaBeispiel.Task2RunCount);
        assertEquals(3200, ahaBeispiel.Task3RunCount);
    }

    @Test
    void SW01_E1_2_CountTaskRunsWith100(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 100;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(300, ahaBeispiel.Task2RunCount);
        assertEquals(20000, ahaBeispiel.Task3RunCount);
    }

    @Test
    void SW01_E1_2_CountTaskRunsWith500(){
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 500;
        ahaBeispiel.task(n);
        assertEquals(4, ahaBeispiel.Task1RunCount);
        assertEquals(1500, ahaBeispiel.Task2RunCount);
        assertEquals(500000, ahaBeispiel.Task3RunCount);
    }
    @Disabled
    @Test
    void SW01_E1_2_StopTimeWithThreadSleepingTaskRunsWith2() throws InterruptedException{
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 2;
        long startMS = System.currentTimeMillis();
        ahaBeispiel.sleepingTask(n);
        long stopMS = System.currentTimeMillis();
        System.out.println("n: " + n + ", time: " + (stopMS - startMS) + " ms");
    }

    @Disabled
    @Test
    void SW01_E1_2_StopTimeWithThreadSleepingTaskRunsWith4() throws InterruptedException{
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 4;
        long startMS = System.currentTimeMillis();
        ahaBeispiel.sleepingTask(n);
        long stopMS = System.currentTimeMillis();
        System.out.println("n: " + n + ", time: " + (stopMS - startMS) + " ms");
    }

    @Disabled
    @Test
    void SW01_E1_2_StopTimeWithThreadSleepingTaskRunsWith20() throws InterruptedException{
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 20;
        long startMS = System.currentTimeMillis();
        ahaBeispiel.sleepingTask(n);
        long stopMS = System.currentTimeMillis();
        System.out.println("n: " + n + ", time: " + (stopMS - startMS) + " ms");
    }

    @Disabled
    @Test
    void SW01_E1_2_StopTimeWithThreadSleepingTaskRunsWith40() throws InterruptedException{
        AhaBeispiel ahaBeispiel = new AhaBeispiel();
        int n = 40;
        long startMS = System.currentTimeMillis();
        ahaBeispiel.sleepingTask(n);
        long stopMS = System.currentTimeMillis();
        System.out.println("n: " + n + ", time: " + (stopMS - startMS) + " ms");
    }
}