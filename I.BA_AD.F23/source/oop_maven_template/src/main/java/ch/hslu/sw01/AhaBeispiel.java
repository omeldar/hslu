package ch.hslu.sw01;

public class AhaBeispiel {
    public int Task1RunCount = 0;
    public int Task2RunCount = 0;
    public int Task3RunCount = 0;

    public void task(final int n) {
        task1(); task1(); task1(); task1(); // T ~ 4
        for (int i = 0; i < n; i++) { // äussere Schleife: n-mal
            task2(); task2(); task2(); // T ~ n · 3
            for (int j = 0; j < n; j++) { // innerer Schleife: n-mal
                task3(); task3(); // T ~ n · n· 2
            }
        }
    }

    public void sleepingTask(final int n) throws InterruptedException{
        sleepingTask1(); sleepingTask1(); sleepingTask1(); sleepingTask1(); // T ~ 4
        for (int i = 0; i < n; i++) { // äussere Schleife: n-mal
            sleepingTask2(); sleepingTask2(); sleepingTask2(); // T ~ n · 3
            for (int j = 0; j < n; j++) { // innerer Schleife: n-mal
                sleepingTask3(); sleepingTask3(); // T ~ n · n· 2
            }
        }
    }
    private void task1(){
        Task1RunCount += 1;
    }
    private void task2(){
        Task2RunCount += 1;
    }
    private void task3(){
        Task3RunCount += 1;
    }

    private void sleepingTask1() throws InterruptedException{
        Thread.sleep(10);
    }
    private void sleepingTask2() throws InterruptedException{
        Thread.sleep(10);
    }
    private void sleepingTask3() throws InterruptedException{
        Thread.sleep(10);
    }
}
